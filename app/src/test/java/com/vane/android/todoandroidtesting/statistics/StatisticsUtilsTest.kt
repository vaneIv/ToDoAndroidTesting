package com.vane.android.todoandroidtesting.statistics

import com.vane.android.todoandroidtesting.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class StatisticsUtilsTest {

    // If there's no completed task and one active task,
    // then there are 100% percent active tasks and 0% completed tasks.
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnZeroHundred() {
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = false)
        )

        // When the list of tasks is computed with a completed task
        val result = getActiveAndCompletedStats(tasks)

        // Then the percentages are 100 and 0
        // In the following lines assertThat(...) we are using (Assertion Framework) Hamcrest.
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(100f))
        // assertEquals(0f, result.completedTasksPercent)
        // assertEquals(100f, result.activeTasksPercent)
    }

    // If there's 2 completed tasks and 3 active tasks,
    // then there are 40% percent completed tasks and 60% active tasks.
    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = false)
        )

        // When the list of tasks is computed with a completed task
        val result = getActiveAndCompletedStats(tasks)

        // Then the result is 40-60
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {

        val tasks = emptyList<Task>()

        // When there are no tasks
        val result = getActiveAndCompletedStats(tasks)
        // Another way to do it, just remove val tasks
        // val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {

        val tasks = emptyList<Task>()

        // When there's an error loading stats
        val result = getActiveAndCompletedStats(tasks)
        // Another way to do it, just remove val tasks
        // val result = getActiveAndCompletedStats(null)

        // Both active and completed tasks are 0
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

}