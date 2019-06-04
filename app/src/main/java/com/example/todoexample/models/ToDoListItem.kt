package com.example.todoexample.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * https://github.com/realm/realm-java/blob/master/examples/kotlinExample/src/main/kotlin/io/realm/examples/kotlin/model/Person.kt
  There are some basic examples on how to use Realm with Kotlin
 */

open class ToDoListItem(
    @PrimaryKey var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var imgUrl: String = ""
) : RealmObject() {
    // The Kotlin compiler generates standard getters and setters.
    // Realm will overload them and code inside them is ignored.
    // So if you prefer you can also just have empty abstract methods.
}