package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.database.PersonDao
import com.example.demo.model.Person
import com.example.demo.model.PersonModel
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import javafx.scene.control.TreeItem
import javafx.scene.layout.BorderPane
import org.controlsfx.control.Notifications
import tornadofx.*
import tornadofx.controlsfx.breadcrumbbar
import tornadofx.controlsfx.infoNotification
import tornadofx.controlsfx.treeitem
import javax.management.Notification

class PersonEditor : View("Person Editor") {
    override val root: BorderPane = BorderPane()
    private val persons: ObservableList<Person>
    private val model: PersonModel = PersonModel(Person())
    private val controller: PersonController by inject()
//    var targetCrumb: TreeItem<String>? = null

    init {
        persons = controller.persons()
        with(root) {
            top {
//                breadcrumbbar<String> {
//                    treeitem("Alpha") {
//                        treeitem("Beta") {
//                            targetCrumb = treeitem("Gamma")
//                        }
//                        treeitem("Zeta")
//                    }
//                    selectedCrumb = targetCrumb
//                }
            }
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
                        buttonbar {
                            button("Reset").action {
                                model.rollback()
                            }
                            button("Clear").action {
                                model.item = Person()
                            }
                            button("Add") {
                                // model.dirty vol dir que ha canviat name/title
                                enableWhen(model.dirty)
                                action {
                                    save()
                                }
                            }
                            button("Delete") {
                                action {
                                    delete()
                                }
                            }
                            button("Update") {
                                // model.dirty vol dir que ha canviat name/title
                                enableWhen(model.dirty)
                                action {
                                    update()
                                }
                            }

                        }

                    }
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        currentWindow?.setOnCloseRequest {
            controller.closeConnection()
        }
    }

    private fun update() {
        val oldPerson = model.item
        if (oldPerson.id > 0) {
            model.commit()
            val newPerson = model.item
            controller.update(newPerson)
            persons.remove(oldPerson)
            persons.add(newPerson)
            persons.sortBy {
                it.name
            }
        }
    }

    private fun save() {
        model.commit()
        val person = model.item
        controller.save(person)
        persons.add(person)
        persons.sortBy {
            it.name
        }
    }

    private fun delete() {
        model.commit()
        val person = model.item
        if (person.id > 0) {
            controller.delete(person)
            persons.remove(person)
        }
    }

}