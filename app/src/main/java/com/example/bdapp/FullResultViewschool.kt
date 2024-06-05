package com.example.bdapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bdapp.SemesterResultView.Companion.ResultItem
import com.example.bdapp.SemesterResultView.Companion.TableCell
import com.example.bdapp.SemesterResultView.Companion.TableHeader
import com.example.bdapp.SemesterResultView.Companion.TableHeaderschool
import com.example.bdapp.SemesterResultView.Companion.TableRow




class FullResultViewschool(
    resultListschool: List<Getdataschool>?,
    navController: NavHostController
) {
    companion object {
        @Composable
        fun FullResultViewSchool(
            results: List<Getdataschool>?,
            navController: NavController,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.sustlogo),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize(),
                    alpha = .3f
                )
            }
            LazyColumn {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.logosust),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }

                item {
                    SemesterResultView.GetPersonInfoschool(
                        university = MainActivity.studentInfoschool?.school_name?:"SUST",
                        name = MainActivity.studentInfoschool?.name ?: "",
                        regName = (MainActivity.studentInfoschool?.reg_no.toString())
                            ?: "", // Corrected typo
                        dept = MainActivity.studentInfoschool?.cls.toString() ?: "",
                        session = MainActivity.studentInfoschool?.session ?: "",
                    )
                }
                item {
                    Text(
                        text = "1st Semester Examination",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color.Magenta,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                item {
                    TableHeaderschool()
                }
                if (results != null) {
                    items(results) { result ->
                        if (result.semester == "1st")
                            SemesterResultView.ResultItemschool(result = result)
                    }
                }
                item {
                     Sum(results, "1st")
                }
                item {
                 //   CumilitiveSum(results,  1)
                    Spacer(modifier = Modifier.height(32.dp))
                }


                item {
                    Text(
                        text = "2nd Semester Examination",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = Color.Magenta,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                item {
                    TableHeaderschool()
                }
                if (results != null) {
                    items(results) { result ->
                        if (result.semester == "2nd")
                            SemesterResultView.ResultItemschool(result = result)
                    }
                }
                item {
                    Sum(results, "2nd")
                    //CumilitiveSum(results,  2)
                }

                item{
                //    CumilitiveSum(results,  2)
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

        }


        private @Composable
        fun Sum(results: List<Getdataschool>?, s: String) {

            fun getLetterGrade(gpa: Double): String {
                return when {
                    gpa >= 4.0 -> "A+"
                    gpa >= 3.75 -> "A"
                    gpa >= 3.5 -> "A-"
                    gpa >= 3.25 -> "B+"
                    gpa >= 3.0 -> "B"
                    gpa >= 2.75 -> "B-"
                    gpa >= 2.5 -> "C+"
                    gpa >= 2.25 -> "C"
                    gpa >= 2.00 -> "C-"
                    else -> "F"
                }
            }

            val sumOfCourseCredit =
                results?.filter { it.GPA != 0.0 && it.semester == s }
                    ?.count()
            val sumOfWeightedCredits =
                results?.filter { it.GPA != 0.0 && it.semester == s }
                    ?.sumByDouble { it.GPA }
            var formattedGpa: Double? = null

            if (sumOfCourseCredit != null && sumOfWeightedCredits != null && sumOfCourseCredit != 0) {
                formattedGpa = sumOfWeightedCredits / sumOfCourseCredit
            }

            val Gpa = formattedGpa?.let { String.format("%.2f", it) } ?: ""
            val Grade = formattedGpa?.let { getLetterGrade(gpa = it) } ?: ""


            TableRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxSize()
            ) {

                TableCell(
                    text = "GPA: " + Gpa,
                    weight = 0.5f,
                    alignment = TextAlign.Left,
                    title = false
                )
                TableCell(
                    text = "Total Grade: "+Grade,
                    weight = 0.5f,
                    alignment = TextAlign.Left,
                    title = false
                )
            }

        }


        private @Composable
        fun CumilitiveSum(results: List<Getdata>?, number: Int) {

            fun getLetterGrade(gpa: Double): String {
                return when {
                    gpa >= 4.0 -> "A+"
                    gpa >= 3.75 -> "A"
                    gpa >= 3.5 -> "A-"
                    gpa >= 3.25 -> "B+"
                    gpa >= 3.0 -> "B"
                    gpa >= 2.75 -> "B-"
                    gpa >= 2.5 -> "C+"
                    gpa >= 2.25 -> "C"
                    gpa >= 2.00 -> "C-"
                    else -> "F"
                }
            }

            val getGPA: List<Double> = cumulativeSum(results)
            val getCredit: List<Double> = cumlativeCredit(results)
            val  sumOfWeightedCredits = getGPA.getOrNull(number - 1) ?: 0.0
            val sumOfCourseCredit = getCredit.getOrNull(number - 1) ?: 0.0

            var formattedGpa: Double? = null

            if (sumOfCourseCredit != 0.0) {
                formattedGpa = sumOfWeightedCredits / sumOfCourseCredit
            }

            val Gpa = formattedGpa?.let { String.format("%.2f", it) } ?: ""
            val Grade = formattedGpa?.let { getLetterGrade(gpa = it) } ?: ""


            TableRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxSize()
            ) {
                TableCell(
                    text = "Total",
                    weight = 0.49f,
                    alignment = TextAlign.Justify,
                    title = true
                )
                TableCell(
                    text = sumOfCourseCredit?.toString() ?: "",
                    weight = 0.185f,
                    alignment = TextAlign.Left,
                    title = false
                )
                TableCell(
                    text = "GPA: " + Gpa,
                    weight = 0.185f,
                    alignment = TextAlign.Left,
                    title = false
                )
                TableCell(
                    text = Grade,
                    weight = 0.185f,
                    alignment = TextAlign.Left,
                    title = false
                )
            }

        }

        fun cumulativeSum(results: List<Getdata>?): List<Double> {
            val cumulativeResult = mutableListOf<Double>()
            var sum = 0.0 // Type of 'sum' is already Double

            for (semester in 1..8) {
                val semesterString = "$semester${getOrdinalSuffix(semester)}"
                sum += getSemesterWiseResult(results, semesterString)
                cumulativeResult.add(sum)
            }

            return cumulativeResult
        }

        private fun cumlativeCredit(results: List<Getdata>?): List<Double> {
            val cumulativeCredit = mutableListOf<Double>()
            var sum = 0.0

            for (semester in 1..8) {
                val semesterString = "$semester${getOrdinalSuffix(semester)}"
                sum += getSemesterCreditsum(results, semesterString)
                cumulativeCredit.add(sum)
            }

            return cumulativeCredit
        }


        private fun getOrdinalSuffix(number: Int): String {
            val suffixes = listOf("", "st", "nd", "rd") + List(7) { "th" }
            return suffixes[number]
        }

        private fun getSemesterWiseResult(results: List<Getdata>?, semester: String): Double {
            var sum = 0.0

            results?.forEach { getData ->
                if (getData.semester == semester) {
                    val credit = getData.course_credit ?: 0.0
                    val gpa = getData.GPA ?: 0.0
                    sum += (credit.toDouble()) * gpa
                }
            }

            return sum
        }


        private fun getSemesterCreditsum(results: List<Getdata>?, semester: String): Double {
            var sum = 0.0

            results?.forEach { getData ->
                val credit = getData.course_credit ?: 0.0
                val gpa = getData.GPA ?: 0.0
                if (getData.semester == semester && gpa != 0.0) {
                    sum += (credit.toDouble())
                }
            }

            return sum
        }


    }
}



