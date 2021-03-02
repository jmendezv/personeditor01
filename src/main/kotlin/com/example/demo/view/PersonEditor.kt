package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.model.Person
import com.example.demo.model.PersonModel
import javafx.collections.ObservableList
import javafx.scene.layout.BorderPane
import tornadofx.*

class PersonEditor : View("Person Editor") {
    override val root: BorderPane = BorderPane()
    private val persons: ObservableList<Person>
    private val model: PersonModel = PersonModel(Person())
    private val controller: PersonController by inject()

    init {
        persons = controller.persons()
        with(root) {
            center {
                tableview(persons) {
                    column("Name", Person::nameProperty)
                    column("Title", Person::titleProperty)

                    // Actualiza la Person de dentro del modelo
//                    model.rebindOnChange(this) { personaSeleccionada ->
//                        item = personaSeleccionada ?: Person()
//                    }
                    // Cuando el modelo extiende ItemViewModel
                    bindSelected(model)
                }
            }

            right {
                form {
                    fieldset("Edit person") {
                        field("Name") {
                            textfield(model.name).required()
                        }
                        field("Title") {
                            textfield(model.title).required()
                        }
                        hbox {
                            button("Reset").action {
                                model.rollback()
                            }
                            button("Add").action {

                            }
                            button("Delete").action {

                            }
                            button("Update") {
                                // model.dirty vol dir que ha canviat name/title
                                enableWhen(model.dirty)
                                action {
                                    save()
                                }
                            }

                        }

                    }
                }
            }
        }
    }

    private fun save() {
        // Passa els canvis des d'els els camps al model
        // Flush changes from the text fields into the model
        model.commit()

        // The edited person is contained in the model
        val person = model.item

        controller.save(person)
    }

}