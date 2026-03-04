package com.example.toeic_app.presentation.writing

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SaveSubmissionDialog(
    onDismiss: () -> Unit,
    onSavePdf: () -> Unit,
    onSubmitOnly: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Save your answer?") },
        text = { Text("Do you want to export your answer alongside the prompt as a PDF file to your device before submitting?") },
        confirmButton = {
            Button(onClick = onSavePdf) {
                Text("Save as PDF")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onSubmitOnly) {
                Text("Submit Only")
            }
        }
    )
}
