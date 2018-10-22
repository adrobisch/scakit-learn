package sklearn.covariance

import java.io.File

import breeze.linalg.{DenseMatrix, DenseVector}
import com.cibo.evilplot.colors.HTMLNamedColors
import com.cibo.evilplot.plot.Plot
import sklearn.util.Csv.loadFile
import sklearn.util.Plot._

object GaussianMultivariateEstimatorApp extends App {
  import com.cibo.evilplot.plot.aesthetics.DefaultTheme._

  val features: Seq[Array[Double]] = loadFile("classpath:anomaly_X.csv", _.map(_.toDouble)).toSeq
  val labels: Seq[Double] = loadFile("classpath:anomaly_y.csv", _.headOption.map(_.toDouble).getOrElse(0.0)).toSeq

  val X = DenseMatrix(features: _*)
  val y = DenseVector(labels: _*)

  val plotOptions = Some((plot: Plot) => plot.xAxis()
    .yAxis()
    .xbounds(lower = 0, upper = 30)
    .ybounds(lower = 0, upper = 30)
    .frame()
    .xLabel("Latency (ms)")
    .yLabel("Throughput (mb/s)"))

  scatter(X, plotOptions, fillColor = Some(HTMLNamedColors.blue)).write(new File("target/scatter.png"))

  val model = new GaussianMultivariateEstimator().fit(X, Some(y))
  val anomaliesIndices = model.predict(X).findAll(_ < 0.0008)

  scatter(X(anomaliesIndices, ::).toDenseMatrix, plotOptions, fillColor = Some(HTMLNamedColors.red)).write(new File("target/anomalies.png"))
}
