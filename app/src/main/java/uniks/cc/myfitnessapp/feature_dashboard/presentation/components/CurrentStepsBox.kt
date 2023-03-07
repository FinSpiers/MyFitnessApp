package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import android.icu.number.NumberFormatter
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme
import java.util.*

@Composable
fun CurrentStepsBox(steps: Int, stepGoal: Int = 10000, dialogState: MutableStateFlow<Boolean>) {
    val progressionPercent = steps.toFloat() / stepGoal.toFloat()
    val stillRemainingPercent = if (1f - progressionPercent > 0f) 1f - progressionPercent else 0f
    val animation: TweenSpec<Float> =
        if (stillRemainingPercent > 0) simpleChartAnimation() else TweenSpec(0, 0)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.large)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Daily steps",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = Icons.Default.BarChart,
                contentDescription = "Show steps history",
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 4.dp, end = 8.dp)
                    .clickable {
                        dialogState.value = !dialogState.value
                        dialogState.tryEmit(dialogState.value)
                    }
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (steps < stepGoal) {
                PieChart(
                    pieChartData = PieChartData(
                        listOf(
                            PieChartData.Slice(
                                progressionPercent,
                                MaterialTheme.colorScheme.primary
                            ),

                            PieChartData.Slice(
                                stillRemainingPercent,
                                MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                    ),
                    animation = animation,
                    sliceDrawer = SimpleSliceDrawer(),
                    modifier = Modifier.size(100.dp),
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(100.dp)
                        .border(12.dp, MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        val text = NumberFormatter.withLocale(Locale.GERMANY).format(steps).toString()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.icon_steps),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 4.dp)
                    .size(32.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$text / 10.000 steps",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}
