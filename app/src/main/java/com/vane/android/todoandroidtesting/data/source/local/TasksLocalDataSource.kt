package com.vane.android.todoandroidtesting.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vane.android.todoandroidtesting.data.Result
import com.vane.android.todoandroidtesting.data.Result.Error
import com.vane.android.todoandroidtesting.data.Result.Success
import com.vane.android.todoandroidtesting.data.Task
import com.vane.android.todoandroidtesting.data.source.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class TasksLocalDataSource internal constructor(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskDataSource {

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return taskDao.observeTasks().map {
            Success(it)
        }
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return taskDao.observeTaskById(taskId).map {
            Success(it)
        }
    }

    override suspend fun refreshTasks() {
        //NO-OP
    }

    override suspend fun refreshTask(taskId: String) {
        //NO-OP
    }

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(taskDao.getTasks())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher) {
        try {
            val task = taskDao.getTaskById(taskId)
            if (task != null) {
                return@withContext Success(task)
            } else {
                return@withContext Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        taskDao.insertTask(task)
    }

    override suspend fun completeTask(task: Task) = withContext(ioDispatcher) {
        taskDao.updateCompleted(task.id, true)
    }

    override suspend fun completeTask(taskId: String) {
        taskDao.updateCompleted(taskId, true)
    }

    override suspend fun activateTask(task: Task) = withContext(ioDispatcher) {
        taskDao.updateCompleted(task.id, false)
    }

    override suspend fun activateTask(taskId: String) {
        taskDao.updateCompleted(taskId, false)
    }

    override suspend fun clearCompletedTasks() = withContext<Unit>(ioDispatcher) {
        taskDao.deleteCompletedTasks()
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        taskDao.deleteTasks()
    }

    override suspend fun deleteTask(taskId: String) = withContext<Unit>(ioDispatcher) {
        taskDao.deleteTaskById(taskId)
    }
}