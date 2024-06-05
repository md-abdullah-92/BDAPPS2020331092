package com.example.bdapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



class SemesterResultView {
    companion object{
        @Composable
        fun SemesterResult(results: List<Getdata>?,navController: NavController,semester:String?) {
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

                item{
                    GetPersonInfo(
                        university = MainActivity.studentInfo?.university_name?:"SUST",
                        name = MainActivity.studentInfo?.name ?: "",
                        regName = MainActivity.studentInfo?.reg_no.toString() ?: "", // Corrected typo
                        dept = MainActivity.studentInfo?.dept.toString()?:"",
                        session = MainActivity.studentInfo?.session ?: "",

                    )
                }

                item {

                    // Examination Title
                    Text(
                        text = "$semester Semester Examination",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color= Color.Magenta,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                }

                // Result Table
                item {
                    TableHeader()
                }
                if(results!=null) {
                    items(results) { result ->
                        ResultItem(result = result)
                    }
                }

                item {
                    val sumOfCourseCredit = results?.filter { it.GPA != 0.0 }?.sumByDouble { it.course_credit.toDouble() }
                    val sumOfWeightedCredits = results?.filter { it.GPA != 0.0 }?.sumByDouble { it.course_credit.toDouble() * it.GPA }
                    var formattedGpa: Double? = null

                    if (sumOfCourseCredit != null && sumOfWeightedCredits != null && sumOfCourseCredit != 0.0) {
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
                        TableCell(text = "Total", weight = 0.49f, alignment = TextAlign.Center, title = false)
                        TableCell(text = sumOfCourseCredit?.toString() ?: "", weight = 0.185f, alignment = TextAlign.Left, title = false)
                        TableCell(text = "GPA: "+(Gpa.toString() ?: ""), weight = 0.185f, alignment = TextAlign.Left, title = false)
                        TableCell(text = Grade, weight = 0.185f, alignment = TextAlign.Left, title = false)
                    }
                }

            }
        }
        /**
         * Composable function to display an individual examination result item.
         *
         * @param result Individual examination result.
         */
        @Composable
        fun ResultItem(result: Getdata?) {
            TableRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TableCell(text = result?.course_code, weight = 0.21f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.course_title, weight = 0.265f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.course_credit.toString(), weight = 0.185f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.GPA.toString(), weight = 0.155f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.Grade, weight = 0.185f, alignment = TextAlign.Left, title = false)
            }
        }
        @Composable
        fun ResultItemschool(result: Getdataschool?) {
            TableRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TableCell(text = result?.course_code, weight = 0.235f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.course_title, weight = 0.265f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.GPA.toString(), weight = 0.25f, alignment = TextAlign.Left, title = false)
                TableCell(text = result?.Grade, weight = 0.25f, alignment = TextAlign.Left, title = false)
            }
        }


        /**
         * Composable function to create a row in the table layout.
         *
         * @param modifier Modifier for the row.
         * @param content Row content.
         */
        @Composable
        fun TableRow(
            modifier: Modifier = Modifier,
            content: @Composable RowScope.() -> Unit
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primaryContainer),
                content = content
            )
        }

        /**
         * Composable function to create a table cell within a row.
         *
         * @param text Text content of the cell.
         * @param weight Weight of the cell.
         * @param alignment Text alignment within the cell.
         * @param title Indicates whether the cell is a title cell.
         */
        @Composable
        fun RowScope.TableCell(
            text: String?,

            weight: Float,
            alignment: TextAlign = TextAlign.Center,
            title: Boolean = false,
        ) {
            if (text != null) {
                Text(
                    text = text,
                    Modifier
                        .weight(weight)
                        .padding(10.dp),
                    fontWeight = if (title) FontWeight.Bold else FontWeight.SemiBold,
                    textAlign = alignment,
                    fontSize = 11.sp
                )
            }
        }

        /**
         * Composable function to create the header row of the result table.
         */
        @Composable
        fun TableHeader() {
            TableRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize()
            ) {
                TableCell(text = "Course No.", weight = 0.21f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Course Title", weight = 0.265f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Credit", weight = 0.185f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Grade Point.", weight = 0.155f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Letter Grade", weight = 0.185f, alignment = TextAlign.Left, title = true)
            }
        }

        @Composable
        fun TableHeaderschool() {
            TableRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxSize()
            ) {
                TableCell(text = "Subject Code", weight = 0.235f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Subject ", weight = 0.265f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Grade Point", weight = 0.25f, alignment = TextAlign.Left, title = true)
                TableCell(text = "Letter Grade", weight = 0.25f, alignment = TextAlign.Left, title = true)
            }
        }


        /**
         * Composable function to display the entire result table.
         *
         * @param results List of examination results to be displayed in the table.
         */
        @Composable
        fun ResultTable(results: List<Getdata>?) {
            LazyColumn {
                item {
                    TableHeader()
                }
                if(results!=null) {
                    items(results) { result ->
                        ResultItem(result = result)
                    }
                }
            }
        }
        /**
         * Composable function to display personal information of a student.
         *
         * @param name Student's name.
         * @param regName Student's registration name.
         * @param session Session information.
         */
        @Composable
        fun GetPersonInfo(university:String,name: String, regName: String, session: String,dept:String) {
            Personalinfo(string="$university")
            Personalinfo(string = "Name of Student :$name")
            Personalinfo(string = "Registration No :$regName")
            Personalinfo(string = "Depertment :$dept")
            Personalinfo("Session :$session")

        }
        @Composable
        fun GetPersonInfoschool(university:String,name: String, regName: String, session: String,dept:String) {
            Personalinfo(string="$university")
            Personalinfo(string = "Name of Student :$name")
            Personalinfo(string = "Roll No :$regName")
            Personalinfo(string = "Class :$dept")
            Personalinfo("Session :$session")

        }
        @Composable
        fun  Personalinfo(string: String){
            Text(
                text = string,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 15.dp)
            )
        }

    }
}