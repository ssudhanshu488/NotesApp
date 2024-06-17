package com.example.notesapp

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.notesapp.data.Notes
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: NoteViewModel,
    navController: NavController
) {
    if (id != 0L){
        val note = viewModel.getNoteById(id).collectAsState(initial = Notes(0L, "", ""))
        viewModel.NotetitleState = note.value.title
        viewModel.NoteDescriptionState = note.value.noteEntered
    }else{
        viewModel.NotetitleState = ""
        viewModel.NoteDescriptionState = ""
    }
    val snackMessage = remember{
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { AppBar(
            title = if (id != 0L) stringResource(id = R.string.update)
            else stringResource(id = R.string.Add)
        ){navController.navigateUp()}
    },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if(viewModel.NotetitleState.isNotEmpty() &&
                        viewModel.NoteDescriptionState.isNotEmpty()){
                        if(id != 0L){
                            viewModel.updateNote(
                                Notes(
                                    id = id,
                                    title = viewModel.NotetitleState.trim(),
                                    noteEntered =viewModel.NoteDescriptionState.trim()
                                )
                            )
                        }else{
                            //  AddWish
                            viewModel.addNote(
                                Notes(
                                    id = id,
                                    title = viewModel.NotetitleState.trim(),
                                    noteEntered =viewModel.NoteDescriptionState.trim()
                                ))
                            snackMessage.value ="Note has been created"
                        }
                    }else{
                        //
                        snackMessage.value = "Enter fields to create a note"
                    }
                    scope.launch {
                        //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }
                },
                icon = { Icon(Icons.Filled.Add, "Save") },
                text = { Text(text = "Save") },
            )
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            EditCardTitleView(value = viewModel.NotetitleState,
                onValueChanged = { viewModel.onNoteTitledChanged(it) })
            EditCardNoteView(value = viewModel.NoteDescriptionState,
                onValueChanged = { viewModel.onNoteDescriptionState(it) })
        }



    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardTitleView(
    value: String,
    onValueChanged: (String) -> Unit
) {


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = "Title",
                color = colorResource(id = R.color.classic_gray),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }, textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        )
    )
}


@Composable
fun EditCardNoteView(
    value: String,
    onValueChanged: (String) -> Unit
) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .imePadding(),
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = "Notes",
                color = colorResource(id = R.color.classic_gray),
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
    )
}

//@Preview(showBackground = true)
//@Composable
//fun FilledCardExamplePreview(){
//    EditCardTitleView("","",{})
//}
