import scala.io.Source
//import scala.collection.JavaConverters._
import scala.jdk.CollectionConverters._

case class RestaurantInfo (
                            name: String,
                            cuisine: String
                          )

case class DiningRecord (
                          name: String,
                          cuisine: String,
                          address: String,
                          timeOfVisit: String)

object RestaurantRecommender {

   def main(args:Array[String]):Unit = {
     if (args.length != 1) {
      println("usage RestaurantRecommender diningHistoryFile.json")
      System.exit(-1)
     }
     val (availableRestaurants, histories) = parseInput(args(0))
     //println(availableRestaurants) 
     //println(histories)
    
   /*
    * First, you have to anonymize the dining histories, by producing
    * the same sized List of List of String's, in which the String values
    * represent the cuisine entry from the original dining histories, ex:
    * [["Mexican", "Roman"], ["Japanese", "Japanese", "American"]] ->outer list is for group of people and the inside list is the type of cuisin
    * */
    val anonymizedHistories = histories.map(x => x.map(a => a.cuisine))
    
    //getFrequencyMap(List("A", "B", "B"))
    makeRecommendation(availableRestaurants, anonymizedHistories)
    
  }

  /*
  * This helper function takes a sequence such as a list
  * and returns a map with keys which correspond to each unique element in the sequence,
  * and the values correspond to the frequency of the keys
  * Ex., for a List["C", "B", "B", "A", "B", "A"] this function will produce
  *       Map["C" -> 1, "B" -> 3, "A" -> 2]
  * */
  def getFrequencyMap[E](sequence: Seq[E]): Map[E, Int] = {
 
   sequence.groupBy(x => x).map{(a => (a._1, a._2.length))}
  }

  /*
  * This helper function takes a map and returns a List of keys, which are sorted in descending order
  * by their values in the Map
  * Ex: for a Map["C" -> 1, "B" -> 3, "A" -> 2], this function will produce
  * List["B", "A", "C"]
  * */
  def fromMapToKeyListSortedByValues(map: Map[String, Int]) : List[String] = {
    map.toList.sortBy(-_._2).map(t => t._1)
  }

  /*
  * This function takes a list of available restaurants at the plaza
  * and a list of anonymized history lists and prints a recommendation
  * that should be formmated as
  * "Your group should consider going to: " + Restaurant Name
  */
  def makeRecommendation(availableRestaurants: List[RestaurantInfo],
                         anonymizedHistories: List[List[String]]): Unit = {
                           //get available restaurants
                           val available = Map(availableRestaurants map {x => (x.cuisine, x.name)} :_*)

                           //list of cuisines
                           val ares = availableRestaurants.map(x => x.cuisine) 

                           //getting annonymized histories
                           val anonymous = anonymizedHistories.flatten 
                           //println(anonymous)
                          
                           //val listCuisine: List[String] = anonymous.intersect(ares)
                           val filteredAnon: List[String] = anonymous.filter(ares.contains(_))
                    
                          //getting the most frequent cuisin in list
                          val rest = getFrequencyMap(filteredAnon)
                          val temp = fromMapToKeyListSortedByValues(rest)
                          val recommend  = available(temp.head)
                          println(s"Your group should consider going to: $recommend")
  }

 /*
  * parseInput takes fileName, which represents a complete
  * path to a JSON file stored on disk.
  * This file contains a list of restaurants at a given shopping plaza and a list of
  * dining histories
  * If the input is successfully parsed, the function returns a tuple, in which
  * the first value is the List of available restaurants, while the second value
  * is the List of List of dining histories
  * */
  private def parseInput(fileName: String): (List[RestaurantInfo], List[List[DiningRecord]]) = {
    val JsonParser = new JsonParser()
    val listDR:java.util.List[java.util.List[DR]] = JsonParser.getDR(fileName)
    val listRI:java.util.List[RI] = JsonParser.getRI(fileName)

    val restaurants:List[RestaurantInfo] =
          listRI.asScala.toList.map(r => RestaurantInfo(r.mName, r.mCuisine))
    val histories = listDR.asScala.toList.map(l => l.asScala.toList.map(
                                                     r => DiningRecord(r.mName, r.mCuisine,
                                                                       r.mAddress, r.mTimeOfVisit)))
    (restaurants, histories)
  }

}
