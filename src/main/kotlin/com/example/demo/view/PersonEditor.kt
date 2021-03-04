package com.example.demo.view

import com.example.demo.controller.PersonController
import com.example.demo.model.Person
import com.example.demo.model.PersonModel
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.input.KeyCombination
import javafx.scene.layout.BorderPane
import tornadofx.*
import tornadofx.controlsfx.infoNotification

class PersonEditor : View("Person Editor") {
    override val root: BorderPane = BorderPane()
    private val persons: ObservableList<Person>
    private val model: PersonModel = PersonModel(Person())
    private val controller: PersonController by inject()

    init {
        persons = controller.persons()
        with(root) {
            top {
                menubar {
                    menu("File") {
                        menu("Connect") {
                            item("Facebook")
                            item("Twitter")
                        }
                        separator()
                        item("Save", KeyCombination.valueOf("Shortcut+S"))
                        item("Quit", KeyCombination.valueOf("Shortcut+Q")).action {
                            Platform.exit()
                        }
                    }
                    menu("Edit") {
                        item("Copy")
                        item("Paste")
                    }
                }
            }
            center {
                tableview(persons) {
                    column("Name", Person::nameProperty)
                    column("Title", Person::titleProperty)
                    bindSelected(model)
                    contextmenu {
                        item("Send Email").action {
                            selectedItem?.apply { println("Sending Email to $name") }
                        }
                        item("Change Status").action {
                            selectedItem?.apply { println("Changing Status for $name") }
                        }
                    }
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
                                enableWhen(model.valid)
                                action {
                                    save()
                                }
                            }
                            button("Delete") {
                                enableWhen(model.valid)
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
        currentWindow?.setOnCloseRequest { controller.closeConnection() }
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
            infoNotification(messages["person_editor"], messages["el_registro_se_ha_actualizado_correctamente"], Pos.CENTER)
        } else {
            infoNotification(messages["person_editor"], messages["no_hay_nada_que_actualizar"], Pos.CENTER)
        }
    }

    private fun save() {
        val oldPerson = model.item
        if (oldPerson.id == 0) {
            model.commit()
            if (!model.isValid) {
                infoNotification(
                    messages["person_editor"],
                    messages["los_campos_no_pueden_quedar_en_blanco"],
                    Pos.CENTER
                )
                return
            }
            val person = model.item
            controller.save(person)
            persons.add(person)
            persons.sortBy {
                it.name
            }
            infoNotification(
                messages["person_editor"],
                messages["el_registro_se_ha_guardado_satisfactoriamente"],
                Pos.CENTER
            )
        } else {
            infoNotification(
                messages["person_editor"],
                messages["no_se_puede_duplicar_el_registro"],
                Pos.CENTER
            )
        }
    }

    private fun delete() {
        model.commit()
        val person = model.item
        if (person.id > 0) {
            messages
            confirm(title = messages["person_editor"], header = messages["estas_seguro"]) {
                controller.delete(person)
                persons.remove(person)
                information(title = messages["person_editor"],
                    header = messages["el_registro_se_ha_eliminado_correctamente"],
                    content = """${person.name} ${messages["se_ha_marcado_como_borrado"]}"""
                )
            }
        } else {
            infoNotification(messages["person_editor"], messages["el_registro_no_esta_presente_en_la_tabla"], Pos.CENTER)
        }
    }

}