package com.example.demo.utils

import com.example.demo.database.PersonEntity
import com.example.demo.model.Person
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.util.Duration
import org.controlsfx.control.Notifications
import org.controlsfx.control.action.Action

object Utils {

    internal fun Person.toEntity(): PersonEntity = PersonEntity(id, name, title)

    internal fun notification(
        title: String?,
        text: String?,
        graphic: Node?,
        position: Pos = Pos.BOTTOM_RIGHT,
        hideAfter: Duration = Duration.seconds(5.0),
        darkStyle: Boolean = false, owner: Any?, vararg actions: Action
    ): Notifications {
        val notification = Notifications
            .create()
            .title(title ?: "")
            .text(text ?: "")
            .graphic(graphic)
            .position(position)
            .hideAfter(hideAfter)
            .action(*actions)
        if (owner != null)
            notification.owner(owner)
        if (darkStyle)
            notification.darkStyle()
        return notification
    }

    fun warningNotification(
        title: String?,
        text: String?,
        position: Pos = Pos.BOTTOM_RIGHT,
        hideAfter: Duration = Duration.seconds(5.0),
        darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
    ) {
        notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
            .showWarning()
    }

    fun infoNotification(
        title: String?,
        text: String?,
        position: Pos = Pos.BOTTOM_RIGHT,
        hideAfter: Duration = Duration.seconds(5.0),
        darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
    ) {
        notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
            .showInformation()
    }

    fun confirmNotification(
        title: String?,
        text: String?,
        position: Pos = Pos.BOTTOM_RIGHT,
        hideAfter: Duration = Duration.seconds(5.0),
        darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
    ) {
        notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
            .showConfirm()
    }

    fun errorNotification(
        title: String?,
        text: String?,
        position: Pos = Pos.BOTTOM_RIGHT,
        hideAfter: Duration = Duration.seconds(5.0),
        darkStyle: Boolean = false, owner: Any? = null, vararg actions: Action
    ) {
        notification(title, text, null, position, hideAfter, darkStyle, owner, *actions)
            .showError()
    }

    fun customNotification(
        title: String?,
        text: String?,
        graphic: Node,
        position: Pos = Pos.BOTTOM_RIGHT,
        hideAfter: Duration = Duration.seconds(5.0),
        darkStyle: Boolean = false, owner: Any? = null,
        vararg actions: Action
    ) {
        notification(title, text, graphic, position, hideAfter, darkStyle, owner, *actions)
            .show()
    }

}