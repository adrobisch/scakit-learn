package sklearn.covariance

import sklearn.math.{Axis, MathContext, Matrix}
import sklearn.{BaseEstimator, EstimatorModel, math}

/**
  * based on the approach in https://www.coursera.org/learn/machine-learning
  *
  * @param mu vector of feature means
  * @param sigma2 covariance matrix
  */
final case class GaussianMultivariateModel(mu: math.Vector[Double],
                                           sigma2: Matrix[Double])(implicit mathContext: MathContext) extends EstimatorModel {
  import mathContext._

  override def predict(input: Matrix[Double]): math.Vector[Double] = {
    val X: Matrix[Double] = input.broadcastRows - mu
    val appliedSigma = (X * pinv(sigma2)) *:* X

    (pow(2 * Math.PI, - mu.length / 2.0) * pow(det(sigma2), -0.5) * exp(-0.5 * sum(appliedSigma, Axis._1))).asVector
  }
}

class GaussianMultivariateEstimator extends BaseEstimator {
  override def fit(X: Matrix[Double], y: Option[math.Vector[Double]])(implicit mathContext: MathContext): GaussianMultivariateModel = {
    import mathContext._

    val m = X.rows.toDouble
    val n = X.cols
    val mu: Matrix[Double] = (sum(X, Axis._0) / m).t

    val sumOfSquaredDiff: Matrix[Double] = sum(pow( X.broadcastRows - mu, 2.0), Axis._0).t
    val nEye = eye[Double](n)
    val sigma2: Matrix[Double] = nEye.broadcastColumns * (sumOfSquaredDiff / m)

    GaussianMultivariateModel(mu.asVector, sigma2)
  }
}
