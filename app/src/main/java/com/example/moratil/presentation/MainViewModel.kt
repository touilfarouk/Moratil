package com.example.moratil.presentation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moratil.data.repository.TodoRepository
import com.example.moratil.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    var todo: Todo by mutableStateOf(Todo(id = 0, task = "", isImportant = false))
        private set

    val getAllTodos: Flow<List<Todo>> = repository.getAllTodos()
    private var deletedTodo: Todo? = null

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo = todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedTodo = todo
            repository.deleteTodo(todo = todo)
        }
    }

    fun undoDeletedTodo() {
        deletedTodo?.let { deleted ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo (todo = deleted)
            }
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedTodo = repository.getTodoById(id = id)
            fetchedTodo?.let {
                todo = it
            }
        }
    }

    fun updateTask(newValue: String) {
        todo = todo.copy(task = newValue)
    }

    fun updateIsImportant(newValue: Boolean) {
        todo = todo.copy(isImportant = newValue)
    }
}