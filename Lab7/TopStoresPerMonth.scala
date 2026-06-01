import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object TopStoresPerMonth {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("TopStoresPerMonth").setMaster("local[4]")
    val sc = new SparkContext(conf)

    val basePath = "../Lab1/"

    val stores = sc.textFile(basePath + "stores.txt").map { line =>
      val parts = line.split(", ")
      (parts(0).toInt, (parts(1), parts(3)))
    }

    val products = sc.textFile(basePath + "products.txt").map { line =>
      val parts = line.split(", ")
      (parts(0).toInt, parts(2).toDouble)
    }

    val sales = sc.textFile(basePath + "sales.txt").map { line =>
      val parts = line.split(", ")
      val date = parts(1)
      val month = date.substring(0, 4) + "-" + date.substring(5, 7)
      (parts(0).toInt, (parts(3).toInt, month))
    }

    val lineItems = sc.textFile(basePath + "lineItems.txt").map { line =>
      val parts = line.split(", ")
      (parts(1).toInt, (parts(2).toInt, parts(3).toInt))
    }

    val revPerLineItem = lineItems
      .join(sales)
      .map { case (saleId, ((productId, qty), (storeId, month))) =>
        (productId, (storeId, month, qty))
      }
      .join(products)
      .map { case (productId, ((storeId, month, qty), price)) =>
        ((storeId, month), qty * price)
      }

    val storeMonthTotal = revPerLineItem.reduceByKey(_ + _)

    val byMonth = storeMonthTotal
      .map { case ((storeId, month), total) => (storeId, (month, total)) }
      .join(stores)
      .map { case (storeId, ((month, total), (name, city))) =>
        (month, (name, city, total))
      }

    val topPerMonth = byMonth
      .groupByKey()
      .mapValues(_.toList.sortBy(-_._3).take(10))
      .sortByKey()

    topPerMonth.collect().foreach { case (month, top) =>
      val formatted = top.map { case (name, city, total) =>
        s"($name, $city, $$${total.toLong})"
      }.mkString(", ")
      println(s"$month, $formatted")
    }

    sc.stop()
  }
}
