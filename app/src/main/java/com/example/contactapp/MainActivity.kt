package com.example.contactapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.ui.theme.ContactAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                AppNavigation()
            }
        }
    }
}

data class savedContact(
    val name: String,
    val address: String,
    val phone: Int,
    val email: String
)



//class Contacts: ViewModel(){
//    private val _contacts = MutableStateFlow(listOf<savedContact>())
//    val contacts: List<savedContact> get() = _contacts
//
//
//    fun addContact(name: String, address: String, phone: Int, email: String){
//        _contacts.
//    }
//}

object Pages{
    const val HOME = "dashboard"
    const val ADD = "add"
    const val EDIT = "edit"
}



@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pages.HOME){
        composable(Pages.HOME) {
            ContactList(navController = navController, modifier = Modifier.fillMaxSize())
        }
        composable(Pages.ADD) {
            AddEditContact(navController = navController, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ContactCard(name: String, address: String){
    Box(Modifier
        .width(350.dp)
        .border(2.dp, Color.Black, RoundedCornerShape(7.dp))) {
        Column(Modifier.padding(10.dp)) {
            Text("Contact 1")
            Row {
                Text("Name : ")
                Text(name)
            }
            Row {
                Text("Address : ")
                Text(address)
            }

        }

    }
    Spacer(Modifier.height(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactList(navController: NavController, modifier: Modifier){


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard", color = Color.White, fontWeight = FontWeight.Bold)},
                colors = TopAppBarDefaults.topAppBarColors(Color.Blue),

            )
        },
        floatingActionButton = {
            Box(contentAlignment = Alignment.BottomEnd) {
               Button(
                    onClick = {
                        navController.navigate(Pages.ADD)
                    },
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    )
                    ) {
                    Icon(painter = painterResource(R.drawable.add), contentDescription = null)
                }
            }
        }
    ){innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Column(Modifier.padding(start = 25.dp, top = 25.dp, end = 25.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                ContactCard("User 1", "Jl. Rawamangun Muka Raya")
                ContactCard("User 2", "Jl. Pemuda Raya")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContact(navController: NavController ,modifier: Modifier){
    var name by remember { mutableStateOf("") }
    var address by remember {mutableStateOf("")}
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Contact", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(Color.Blue),

            )
        }
    ) {innerPadding ->
        Column(modifier.padding(innerPadding)) {
            Column(Modifier.padding(start = 55.dp, top = 25.dp, end = 55.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(
                    value = name,
                    onValueChange = {newName ->
                        name = newName
                    },
                    label = {Text("Name")}
                )
                Spacer(Modifier.padding(top = 10.dp))
                TextField(
                    value = address,
                    onValueChange = {newAddress ->
                        address = newAddress
                    },
                    label = {Text("Address")}
                )
                Spacer(Modifier.padding(top = 10.dp))
                TextField(
                    value = phone,
                    onValueChange = {newPhone ->
                        phone = newPhone
                    },
                    label = {Text("Phone")}
                )
                Spacer(Modifier.padding(top = 10.dp))
                TextField(
                    value = email,
                    onValueChange = {newEmail ->
                        email = newEmail
                    },
                    label = {Text("Email")}
                )
                Spacer(Modifier.height(30.dp))
                Button(
                    onClick = {
                        navController.navigate(Pages.HOME)
                    },
                    modifier = Modifier.height(50.dp).width(250.dp)
                ) {
                    Text("Add Contact", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactListPreview() {
    ContactAppTheme {
        ContactList(rememberNavController(),Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun AddContactPreview() {
    ContactAppTheme {
        AddEditContact(rememberNavController(), Modifier.fillMaxSize())
    }
}