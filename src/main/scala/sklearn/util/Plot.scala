package sklearn.util

import breeze.linalg.{*, DenseMatrix}
import com.cibo.evilplot.colors.{Color, HEX}
import com.cibo.evilplot.geometry.{Drawable, Extent, Group, Line, Rotate}
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot.{Plot, ScatterPlot}
import com.cibo.evilplot.plot.aesthetics.Theme
import com.cibo.evilplot.plot.renderers.PointRenderer

object Plot {
  private def scatterRenderer(color: Option[Color] = None,
                      strokeWidth: Option[Double] = None,
                      pointSize: Option[Double] = None)(implicit theme: Theme): PointRenderer = new PointRenderer {
    override def render(plot: Plot, extent: Extent, index: Int): Drawable = {
      val size = pointSize.getOrElse(8.0)
      val stroke = strokeWidth.getOrElse(0.7)

      Group(Seq(
        Rotate(Line(size, stroke).colored(color.getOrElse(theme.colors.fill)), 45),
        Rotate(Line(size, stroke).colored(color.getOrElse(theme.colors.fill)), 135)
      ))
    }
  }

  def scatter[V](matrix: DenseMatrix[V],
                 plotOptions: Option[Plot => Plot],
                 fillColor: Option[Color] = None,
                 xcol: Int = 0,
                 ycol: Int = 1)(implicit numeric: Numeric[V], theme: Theme): Drawable = {
    val points = matrix(*, ::).map(row => Point(numeric.toDouble(row(xcol)), numeric.toDouble(row(ycol)))).data
    val basePlot = ScatterPlot(points, pointRenderer = Some(scatterRenderer(color = fillColor)))

    plotOptions
      .getOrElse(identity[Plot](_))(basePlot)
      .background(color = HEX("#ffffff"))
      .render()
  }
}
