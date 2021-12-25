package com.capstone.capstone_group.isklinika;

import java.util.ArrayList;

public interface InterfaceIsklinika {

    void buildBar() ; /*This function is used to build the bar at the top and bottom of the screen*/
    void buildViews(); //This is used to build the common views of the activity
    void checkUser() ; //This function is used to check the userType of the user.
//    void dataInChildren(ArrayList<ClassStudentInfo> childrenInfo) ;  //This function is used to populate the recyclerview that shows the icons of the children. used when userType == Parent
    void retrieveDataParentUser() ; /*This function retrieves the data of the parent's child/children in the database.
                                        The retrieved data will be displayed and put in the dropdown.
                                         When the user is a parent. */
}
