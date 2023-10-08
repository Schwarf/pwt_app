package abs.apps.personal_workout_tracker.data.http_client

import abs.apps.personal_workout_tracker.data.database.Workout
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
class DTOConverter<Input: Any, Output: Any> (
    private val inputClass: KClass<Input>,
    private val outputClass: KClass<Output>
){
    private val inputProperties = inputClass.memberProperties
    private val outputProperties = outputClass.memberProperties

    fun convert(input: Input): Output {
        val outputMap = mutableMapOf<KProperty1<Output, *>, Any?>()

        inputProperties.forEach { inputProperty ->
            if (inputProperty.name != "lastModified") {
                val outputProperty = outputProperties.find { it.name == inputProperty.name }
                if (outputProperty != null) {
                    outputMap[outputProperty] = inputProperty.get(input)
                }
            }
        }
        // use map to create outputParameterMap
        val outputParameterMap = outputMap.entries.associate { (key, value) ->
            val parameter = outputClass.constructors.first().parameters.find { it.name == key.name }
            parameter!! to value
        }

        return outputClass.constructors.first().callBy(outputParameterMap)
    }


}