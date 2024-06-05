package com.example.bdapp

data class  Getdata (
    // Represents a data object containing information about a student's course.
    val reg_no: Int,
    val GPA:Double,
    val Grade: String,
    val course_code : String,
    val course_title: String,
    val semester : String,
    val course_credit: Float,
    val eiin:String
)
data class StudentInfo(
    // Represents a data object containing information about a student.
    val name : String,
    val reg_no: Int,
    val date_of_birth :String,
    val dept : String,
    val session: String,
    val university_name: String,
    val eiin:String
)
data class  Getdataschool (
    // Represents a data object containing information about a student's course.
    val reg_no: Int,
    val GPA:Double,
    val Grade: String,
    val course_code : String,
    val course_title: String,
    val semester : String,
    val cls: String,
    val eiin: String
)
data class StudentInfoschool(
    // Represents a data object containing information about a student.
    val name : String,
    val reg_no: Int,
    val date_of_birth :String,
    val cls : String,
    val session: String,
    val school_name: String,
    val eiin: String
)
data class Eiininfo (
    val eiin: String,
    val phone:String,
    val institution_name : String,
    val  email: String,
    val registerstatus: Boolean
)