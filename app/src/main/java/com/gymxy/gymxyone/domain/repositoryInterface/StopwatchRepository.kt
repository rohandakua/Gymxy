package com.gymxy.gymxyone.domain.repositoryInterface

interface StopwatchRepository {
    /**
     * start() is used to start the stopwatch.
     */
    suspend fun start()

    /**
     * reset() is used to reset the stopwatch. It makes the time to absolute zero.
     */
    fun reset()

    /**
     * pause() is used to pause the stopwatch. It stores the data in viewModel.
     */
    fun pause()

    /**
     * getTimeInMillis() is used to get the time in milliseconds.
     * @return time in milliseconds. Convert this to first time in seconds and then to string to show the data.
     */
    fun getTimeInMillis():Long

    /**
     * isRunning() is used to get the info about the stopwatch whether it is running or not.
     * @return gives a boolean.
     */
    fun isRunning():Boolean

}