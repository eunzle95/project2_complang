Project2 README
===
written by Sam Heo and Eunoh Cho
---

Specification about the project2
>Skills
    groupBy
    map
    flatten
    filter
>Use of the skills
    -groupBy
    For grouBy we got to know that it groups up the variable we would like to find in the list when it is used in list
    it is used in the function called
    def getFrequencyMap[E](sequence: Seq[E])


    -map
    For map after we used groupBy function, we use the map function to put the variables in map format
    it is used in the function called
    def getFrequencyMap[E](sequence: Seq[E])
    to print out the output in form of
        Map["C" -> 1, "B" -> 3, "A" -> 2]
    

    -flatten
    For flatten we have gotten a knowledge to figure out that flatten would allow us to view the list in all flattened view
    For example
        List[["a", "b"], ["b", "c"]]
        if we flatten the list it would show us to view the list in form of
        ["a", "b", "b", "c"]
        This skill is used in function called 
        def makeRecommendation(availableRestaurants: List[RestaurantInfo],anonymizedHistories: List[List[String]])
        that we get to flatten the list of the dining history of various people.

    -filter
    Another skill we got used to was filter.
    filter is the function the filter out variables from list.
    For example
        list = ["a", "b", "c", "c", "d"] and we would like to only see the "c" in the list
    after using filter skill 
    the list would only show up as ["c", "c"]

