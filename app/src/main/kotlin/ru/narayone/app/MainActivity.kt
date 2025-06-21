package ru.narayone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.narayone.featureauth.WelcomeScreen
import ru.narayone.featureauth.LoginScreen
import ru.narayone.featureauth.SignUpScreen
import ru.narayone.featurehousedetail.HouseDetailScreen
import ru.narayone.featuremap.MapScreen
import ru.narayone.featuremain.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }
                    var selectedHouseId by remember { mutableStateOf<String?>(null) }
                    
                    when (currentScreen) {
                        Screen.Welcome -> {
                            WelcomeScreen(
                                onContinueClick = { currentScreen = Screen.Login }
                            )
                        }
                        Screen.Login -> {
                            LoginScreen(
                                onLoginClick = { currentScreen = Screen.Main },
                                onSignUpClick = { currentScreen = Screen.SignUp }
                            )
                        }
                        Screen.SignUp -> {
                            SignUpScreen(
                                onCreateAccountClick = { currentScreen = Screen.Main },
                                onLoginClick = { currentScreen = Screen.Login }
                            )
                        }
                        Screen.Main -> {
                            MainScreen(onMapClick = { currentScreen = Screen.Map })
                        }
                        Screen.Map -> {
                            MapScreen(onHouseClick = { houseId ->
                                selectedHouseId = houseId
                                currentScreen = Screen.HouseDetail
                            })
                        }
                        Screen.HouseDetail -> {
                            // Используем экран детальной информации
                            selectedHouseId?.let { houseId ->
                                HouseDetailScreen(
                                    houseId = houseId,
                                    onBackClick = { currentScreen = Screen.Map }
                                )
                            } ?: run {
                                // Если ID дома не выбран, возвращаемся на карту
                                currentScreen = Screen.Map
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen {
    object Welcome : Screen()
    object Login : Screen()
    object SignUp : Screen()
    object Main : Screen()
    object Map : Screen()
    object HouseDetail : Screen()
} 