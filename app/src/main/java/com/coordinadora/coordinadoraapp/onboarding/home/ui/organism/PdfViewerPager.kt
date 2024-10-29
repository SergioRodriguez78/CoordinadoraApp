package com.coordinadora.coordinadoraapp.onboarding.home.ui.organism

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.coordinadora.coordinadoraapp.onboarding.home.ui.molecules.PagerStatus
import kotlinx.coroutines.launch


@Composable
fun PdfViewerPager(bitmaps: List<Bitmap>) {

    val totalPages = bitmaps.size
    val pagerState = rememberPagerState(pageCount = { totalPages })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->

            Image(
                bitmap = bitmaps[page].asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        PagerStatus(
            currentPage = pagerState.currentPage,
            totalPages = totalPages,
            onPreviousClick = {
                scope.launch {
                    if (pagerState.currentPage > 0) {
                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                    }
                }
            },
            onNextClick = {
                scope.launch {
                    if (pagerState.currentPage < totalPages - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )
    }
}
