package com.example.sendmoney_mansoor.ui.screens.requests

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sendmoney_mansoor.data.repository.SavedRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestsScreen(
    onViewDetails: (String) -> Unit,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val requests by viewModel.requests.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Saved Requests",
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF686F89),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFedf1f8)
    ) { innerPadding ->
        if (requests.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No requests found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(requests) { request ->
                    RequestRow(
                        request = request,
                        onViewDetails = { onViewDetails(request.id) }
                    )
                }
            }
        }
    }
}


@Composable
fun RequestRow(
    request: SavedRequest,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(label = "Request ID:", value = request.id.take(8) + "...")
            InfoRow(label = "Service name:", value = request.serviceName)
            InfoRow(label = "Provider name:", value = request.providerName)
            InfoRow(label = "Amount:", value = "${request.amount} AED")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onViewDetails,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "View Details")
            }
        }
    }
}


@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.titleMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

