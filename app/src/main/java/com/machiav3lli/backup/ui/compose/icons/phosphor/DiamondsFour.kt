package com.machiav3lli.backup.ui.compose.icons.phosphor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.machiav3lli.backup.ui.compose.icons.Phosphor

val Phosphor.DiamondsFour: ImageVector
    get() {
        if (_diamonds_four != null) {
            return _diamonds_four!!
        }
        _diamonds_four = Builder(
            name = "Diamonds-four",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 256.0f,
            viewportHeight = 256.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(116.7f, 104.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, 22.6f, 0.0f)
                lineTo(168.0f, 75.3f)
                arcToRelative(16.1f, 16.1f, 0.0f, false, false, 0.0f, -22.6f)
                lineTo(139.3f, 24.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, -22.6f, 0.0f)
                lineTo(88.0f, 52.7f)
                arcToRelative(16.1f, 16.1f, 0.0f, false, false, 0.0f, 22.6f)
                close()
                moveTo(128.0f, 35.3f)
                lineTo(156.7f, 64.0f)
                lineTo(128.0f, 92.7f)
                horizontalLineToRelative(0.0f)
                lineTo(99.3f, 64.0f)
                close()
                moveTo(232.0f, 116.7f)
                lineTo(203.3f, 88.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, -22.6f, 0.0f)
                lineTo(152.0f, 116.7f)
                arcToRelative(16.1f, 16.1f, 0.0f, false, false, 0.0f, 22.6f)
                lineTo(180.7f, 168.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, 22.6f, 0.0f)
                lineTo(232.0f, 139.3f)
                arcTo(16.1f, 16.1f, 0.0f, false, false, 232.0f, 116.7f)
                close()
                moveTo(192.0f, 156.7f)
                horizontalLineToRelative(0.0f)
                lineTo(163.3f, 128.0f)
                lineTo(192.0f, 99.3f)
                lineTo(220.7f, 128.0f)
                close()
                moveTo(104.0f, 116.7f)
                lineTo(75.3f, 88.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, -22.6f, 0.0f)
                lineTo(24.0f, 116.7f)
                arcToRelative(16.1f, 16.1f, 0.0f, false, false, 0.0f, 22.6f)
                lineTo(52.7f, 168.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, 22.6f, 0.0f)
                lineTo(104.0f, 139.3f)
                arcTo(16.1f, 16.1f, 0.0f, false, false, 104.0f, 116.7f)
                close()
                moveTo(64.0f, 156.7f)
                horizontalLineToRelative(0.0f)
                lineTo(35.3f, 128.0f)
                lineTo(64.0f, 99.3f)
                lineTo(92.7f, 128.0f)
                close()
                moveTo(139.3f, 152.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, -22.6f, 0.0f)
                lineTo(88.0f, 180.7f)
                arcToRelative(16.1f, 16.1f, 0.0f, false, false, 0.0f, 22.6f)
                lineTo(116.7f, 232.0f)
                arcToRelative(15.9f, 15.9f, 0.0f, false, false, 22.6f, 0.0f)
                lineTo(168.0f, 203.3f)
                arcToRelative(16.1f, 16.1f, 0.0f, false, false, 0.0f, -22.6f)
                close()
                moveTo(128.0f, 220.7f)
                horizontalLineToRelative(0.0f)
                lineTo(99.3f, 192.0f)
                lineTo(128.0f, 163.3f)
                lineTo(156.7f, 192.0f)
                close()
            }
        }
            .build()
        return _diamonds_four!!
    }

private var _diamonds_four: ImageVector? = null
