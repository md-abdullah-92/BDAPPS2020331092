package com.example.bdapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bdapp.FullResultView.Companion.Fullresultview
import com.example.bdapp.FullResultViewschool.Companion.FullResultViewSchool
import com.example.bdapp.HomePage.Companion.Homepage
import com.example.bdapp.Otpverifiy.Companion.EnterOTP
import com.example.bdapp.Resultpage.Companion.ResultPage
import com.example.bdapp.SchoolSubmitPage.Companion.Submitschool
import com.example.bdapp.SemesterResultView.Companion.SemesterResult
import com.example.bdapp.StartPage.Companion.Startpage
import com.example.bdapp.SubmitPage.Companion.Submit
import com.example.bdapp.databaseinfo.Companion.Databaseinfo
import com.example.bdapp.eiinnumberverifiy.Companion.EnterEIIN
import com.example.bdapp.subscribepage.Companion.SubscribePage
import com.example.bdapp.ui.theme.BdappTheme


class MainActivity : ComponentActivity() {
    companion object DataManager {
        // Variables to store student information, result list, and semester
        var studentInfo: StudentInfo? = null
        var resultList: List<Getdata>?= null
        var studentInfoschool: StudentInfoschool? = null
        var resultListschool1: List<Getdataschool>?= null
        var semester:String?=null
        var p=""
        val requestParameters = RequestParameters(
            appId = "APP_119158",
            password = "6a553912e964f8ec308cd563b034fad1",
            mobile = ""
        )
        val verifyParameters = VerifyParameters(
            appId = "APP_119158",
            password = "6a553912e964f8ec308cd563b034fad1",
            referenceNo = "",
            otp = ""
        )


    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BdappTheme {
                // Surface container using the background color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Remembering the nav controller state
                    val navController = rememberNavController()
                    // Setting up navigation host with start destination
                    NavHost(
                        navController = navController,
                        startDestination = "startpage"
                    ) {
                        // Composable for the start page
                        composable("startpage") {
                            Startpage(navController)
                        }
                        // Composable for the home page
                        composable("homepage") {
                            Homepage(navController)
                        }

                        // Composable for the submit page
                        composable("submit") {
                            Submit((navController))
                        }
                        composable("submitschool") {
                            Submitschool((navController))
                        }

                        // Composable for the semester result page
                        composable("semesterresult") {
                            SemesterResult( resultList,navController, semester)
                        }
                        composable("fullresult") {
                            Fullresultview( resultList,navController)
                        }
                        composable("fullresultschool") {
                            FullResultViewSchool( resultListschool1,navController)
                        }

                        // Composable for the result page
                        composable("resultpage") {
                            ResultPage(navController)
                        }

                       composable("entereiin"){
                           EnterEIIN(navController)
                       }
                        composable("enterotp"){
                            EnterOTP(navController)
                        }
                        composable("subscribe"){
                            SubscribePage(navController)
                        }
                        composable("unsubscribe"){
                            Databaseinfo(navController)
                        }
                    }

                }
            }
        }
    }
}





