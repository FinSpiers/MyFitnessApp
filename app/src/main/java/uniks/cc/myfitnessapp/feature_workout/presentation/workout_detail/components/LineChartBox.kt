package uniks.cc.myfitnessapp.feature_workout.presentation.workout_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

@Composable
fun LineChartBox(title: String, xDataList: List<String>, yDataList: List<Float>) {

    if ((yDataList.size != xDataList.size) || (yDataList.size < 2)) {
        return
    }
    val points = arrayListOf<LineChartData.Point>()
    for (i in 0 until (yDataList.size)) {
        points.add(
            LineChartData.Point(
                yDataList[i],
                xDataList[i]
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White, shape = MaterialTheme.shapes.large)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.large),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(8.dp),
                color = Color.Black,
                style = MaterialTheme.typography.headlineSmall
            )
            LineChart(
                modifier = Modifier.fillMaxSize(0.9f),
                animation = simpleChartAnimation(),
                xAxisDrawer = SimpleXAxisDrawer(),
                yAxisDrawer = SimpleYAxisDrawer(),
                linesChartData = listOf(
                    LineChartData(
                        points = points,
                        lineDrawer = SolidLineDrawer()
                    )
                )
            )
        }
    }
}