# Scottish Power Interview Task

## Overview

The app is a basic implementation of the interview task's requirements, with some small additions. Rather than simply displaying the contents of the Placeholder API's `/albums/` endpoint, I chained some calls together to show the author's username and a thumbnail from one of the pictures in the album. This unfortunately results in some overhead when launching the app; I believe the test API is likely a little slow or throttled, however I felt this was acceptable as it allowed me to flesh out the main screen with more interesting contents. Please be aware, however, that the app will take a few seconds to download all the data. The API also seems a bit slow at downloading images, at least on my end. I added some placeholder thumbnails to reflect loading and error states to assist with rendering the rest of the screen - they are slightly ugly and not necessarily reflective of what I would put into a finished product.

I expanded upon the sorting requirements and added buttons that allow the user to sort by username or title.

I also implemented an album details screen, which shows the author's information and a gallery of the album's contents.

## Architecture

For this task, I went with an MVVM architecture. Given the relatively simple nature of the app, most architectures would have done, therefore I chose MVVM because:

1) It's a modern architecture advocated by Google
2) I wanted to mirror Scottish Power's architecture

One issue was defining the top-level composable for each screen as an extension of NavGraphBuilder: while it makes the AppNavigation class very clean and scaleable, it does not lend itself to using the Compose preview feature and could use some further separation of functionality.

I also attempted to follow clean architecture principals which I believe have been implemented in a relatively conventional way.

## Issues and potential improvements

As mentioned, there is some overhead in downloading all the data when the app boots. The networking could have been improved to allow results to trickle in and individually render, which would be a better user experience than waiting for all results. Additionally, some loading/error indicators on the detail screen would be useful, particularly as the API is a little slow and seems a bit brittle for downloading images.

Additionally, caching could have been implemented to improve performance. To do this, I would implement a local repository alongside the remote, and within the domain layer decide whether to load the local or remote data.

While the user is able to reverse the sort direction by selecting the sort button an additional time, this is not necessarily obvious from the UI and perhaps could be clarified by a dynamic icon reflecting the direction of the sort.

The testing was fairly rudimentary, particularly the integration testing, which only explored that the UI rendered correctly and buttons updated accordingly and did not account for any error states the app may result in.
