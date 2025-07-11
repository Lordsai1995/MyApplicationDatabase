package com.example.myapplicationdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // ✅ Create your ViewModel using factory (because it takes Application)
            val userViewModel: UserViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return UserViewModel(application) as T
                    }
                }
            )

            Scaffold {paddingValues ->
                UserScreen(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp),
                    viewModel = userViewModel
                )
            }
            // ✅ Show the UI and pass the ViewModel
        }
    }
}


@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (name.isNotBlank()) {
                viewModel.addUser(name)
                name = ""
            }
        }) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(56.dp))
        Text("Saved Users:")
        for (user in viewModel.userList) {
            Text("- ${user.name}")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }
}




