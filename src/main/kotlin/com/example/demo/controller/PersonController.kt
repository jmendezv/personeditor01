package com.example.demo.controller

import com.example.demo.model.Person
import javafx.collections.ObservableList
import tornadofx.*

class PersonController : Controller() {

    fun save(person: Person?) {
        println("Saving ${person?.name} / ${person?.title}")
    }
    
    /*
    * */
    fun persons(): ObservableList<Person> = listOf(
        Person("John", "Manager"),
        Person("Jay", "Worker bee")).observable()
    
}