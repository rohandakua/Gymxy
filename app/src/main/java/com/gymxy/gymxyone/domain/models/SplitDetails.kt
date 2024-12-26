package com.gymxy.gymxyone.domain.models

/**
 * splitId is the email followed by the no. of custom split user has performed eg. rd@gmail.com1
 * 4 split will be predefined and after that the no. of the splits the user has performed will be added to the email at the back\
 * splitName has a basic name of the split
 * details is the map for the days the user has selected for a split for eg.
 * a custom split name abc has 3 days , {0-> legs, 1-> push , 2-> pull}
 */
data class SplitDetails(
    var splitId : String = "",
    var splitName : String = "Split Name",
    var details : Map<Int , String> = mapOf()
)