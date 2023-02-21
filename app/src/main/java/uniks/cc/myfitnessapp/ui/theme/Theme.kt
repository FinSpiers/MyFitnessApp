package uniks.cc.myfitnessapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,
    inversePrimary = Blue40,

    secondary = DarkBlue80,
    onSecondary = DarkBlue20,
    secondaryContainer = DarkBlue30,
    onSecondaryContainer = DarkBlue90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = Gray10,
    onBackground = Gray90,
    surface = BlueGray30,
    onSurface = BlueGray80,
    inverseSurface = Gray90,
    inverseOnSurface = Gray10,
    surfaceVariant = BlueGray30,
    onSurfaceVariant = BlueGray80,
    outline = BlueGray80
)

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,
    inversePrimary = Blue80,

    secondary = DarkBlue40,
    onSecondary = Color.White,
    secondaryContainer = DarkBlue90,
    onSecondaryContainer = DarkBlue10,

    error = Red80,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,

    background = Gray99,
    onBackground = Gray10,
    surface = BlueGray90,
    onSurface = BlueGray30,
    inverseSurface = Gray10,
    inverseOnSurface = Gray90,
    surfaceVariant = BlueGray90,
    onSurfaceVariant = BlueGray30,
    outline = BlueGray50
)

@Composable
fun MyFitnessAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}