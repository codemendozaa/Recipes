package com.example.recipes.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.recipes.R
import com.example.recipes.data.model.RecipeDTO
import com.example.recipes.presentation.map.ViewMap
import com.example.recipes.ui.theme.*

@Composable
fun DetailScreen(
    navHostController: NavHostController,
    recipeId: Int,
    detailViewModel: DetailViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        detailViewModel.getSelectedRecipe(recipeId = recipeId)
    }
    val recipeSelected by detailViewModel.recipeSelected.collectAsState()

    LaunchedEffect(key1 = recipeSelected) {
        recipeSelected?.let {

        }
    }

    recipeSelected?.let {
        DetailContent(recipe = it,  onCloseClicked = {
            navHostController.popBackStack()
        })
    }
}


@ExperimentalCoilApi
@Composable
fun DetailContent(recipe: RecipeDTO, onCloseClicked: () -> Unit) {
    val modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(horizontal = MEDIUM_PADDING)

    val painterCharacter = rememberImagePainter(data = recipe.image) {
        placeholder(R.drawable.ic_image)
        error(R.drawable.ic_broken_image)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn() {
            item {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LARGE_PADDING, vertical = LARGE_PADDING)
                ) {
                    val (characterName, closeButton) = createRefs()
                    Text(
                        text = recipe.name,
                        modifier = Modifier.constrainAs(characterName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.textColor
                    )
                    IconButton(
                        modifier = Modifier
                            .constrainAs(closeButton) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            },
                        onClick = { onCloseClicked() }) {
                        Icon(
                            modifier = Modifier.size(INFO_ICON_SIZE),
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close_icon),
                            tint = MaterialTheme.colors.textColor
                        )
                    }
                }
                Image(
                    painter = painterCharacter,
                    contentDescription = stringResource(id = R.string.recipes_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(CHARACTER_ITEM_HEIGHT)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    color = MaterialTheme.colors.topAppBarBackgroundColor
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MEDIUM_PADDING)
                    ) {
                        Text(
                            text = stringResource(id = R.string.information_label),
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.LightGray,
                            fontWeight = FontWeight.ExtraBold

                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = recipe.description,
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colors.textColor,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        maxLines = 6,
                        overflow = TextOverflow.Clip,


                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ViewMap()

                }

            }

        }
    }
}




@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    DetailContent(
        recipe = RecipeDTO(
            id = 0,
            image = "",
            name = "Erix",
            description = "lorem ipsup"
        ),
        onCloseClicked = {},

    )
}