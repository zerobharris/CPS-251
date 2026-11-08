package com.example.grademanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainViewModel : ViewModel() {

    var students by mutableStateOf<List<Student>>(emptyList())
        private set // 'private set' means the 'students' list can be read externally but only modified within this ViewModel.

    var isLoading by mutableStateOf(false)
        private set

    var newStudentName by mutableStateOf("")
        private set

    var newStudentGrade by mutableStateOf("")
        private set

    fun addStudent(name: String, grade: String) {
        // TODO: Implement the addStudent function
        val gradeValue = grade.toFloatOrNull()

        if (name.isNotBlank() && gradeValue != null) {
            val student = Student(name, gradeValue)
            students = students + student

            newStudentName = ""
            newStudentGrade = ""
        }
    }

    fun removeStudent(student: Student) {
        // TODO: Implement the removeStudent function
        students = students - student
    }

    fun calculateGPA(): Float {
        // TODO: Implement the calculateGPA function
        if (students.isEmpty()) {
            return 0f // Placeholder
        }
        return students.map { it.grade }.average().toFloat()
    }

    fun loadSampleData() {
        // TODO: Implement the loadSampleData function using coroutines (viewModelScope.launch)
        viewModelScope.launch {
            isLoading = true
            delay(1500)

            students = listOf(
                Student("Alice Johnson", 95f),
                Student("Bob Smith", 87f),
                Student("Carol Davis", 92f)
            )
            isLoading = false
        }
    }

    fun updateNewStudentName(name: String) {
        // TODO: Implement the updateNewStudentName function
        newStudentName = name
    }

    fun updateNewStudentGrade(grade: String) {
        // TODO: Implement the updateNewStudentGrade function
        newStudentGrade = grade
    }
}

data class Student(
    val name: String,
    val grade: Float
)
