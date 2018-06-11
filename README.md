# ArtistSongSearch
This is an assigment I was given to complete for the position that I am currently holding.

The instructions for the project can be found below.

Create an Android App that has the following pages:

1. Main Page
1.1 Shows a search field at the top with placeholder text “artist name” and 2 buttons “Clear” and “Search”

1.2 When search is tapped, the app should query the iTunes api for results. Api info can be found here.

1.3 When the search button is tapped, the api should be queried and any results of kind “song” should be displayed in a listview below the search bar. Each row of the listview should show the artwork (api results contain a url to them) on the left  and on the right the song name and artist name. When a row is tapped, it should show the Detail Page.

1.4 The clear button should clear the search bar and remove results from the listview.

2. Detail Page

2.1 Shows the artwork, song name, album name, artist name and genre.

2.2 Also shows a “Play Preview”” button which will play the song preview (api results contain a url to it)

2.3 Also shows a “Share” button which will let the user email the details on the page to anyone.

Notes

• The app does not have to be pretty - it should just show the pages and fields as specified
• Use your initiative to deal with possible errors like no search text, no search results or no internet connectivity
