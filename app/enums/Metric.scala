package enums

object Metric extends Enumeration {
  type Metric = Value

  val SERVED: enums.Metric.Value = Value
  val DOWNLOADED: enums.Metric.Value = Value
  val VIEWABLE: enums.Metric.Value = Value
}
