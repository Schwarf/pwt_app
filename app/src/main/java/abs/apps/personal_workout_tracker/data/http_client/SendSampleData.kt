package abs.apps.personal_workout_tracker.data.http_client

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result

fun sendData() {
    // Set up FuelManager to allow HTTP connections if your FastAPI server uses HTTP.
    FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")

    val url = "http://10.0.2.2:8000/insert_workouts/"
    val jsonData = """
        { "workouts": 
          [
            {
              "name": "SendFromAndroid",
              "sets": 5,
              "totalRepetitions": 50,
              "maxRepetitions": 15,
              "performances": 3,
              "id": 1,
              "isDeleted": 0
            }
          ]
        }
    """

    Fuel.post(url).jsonBody(jsonData).response { _, _, result ->
        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                println("Error: $ex")
            }
            is Result.Success -> {
                val data = result.get()
                println("Response: ${String(data)}")
            }
        }
    }
}
