# **Open Movie Database Example App**
## A simple Android client for Open Movie DB in Material Design.

*This project is an Android app which displays data from [The Movie Database API](http://api.themoviedb.org).*

**Goals**

-1. Screen 1 - Search for movies A search button that reveals the text enter and submit button. Show list of movies found in the same screen. Lazy Load future sets of data from pagination added to the list.
  - On clicking the image, it displays a full screen of the poster image. 
  - On clicking the text, user is taken to IMDB.com's profile of the movie for more imformation.

**Notes**: With this app I focused on using HTTP to parse OMDB's API and retrieve the JSON information needed.
First I used Apache.HTTP to make a request for the user's search query and page number. 

I Chose to use Glide on this app to load all images from the internet in order to handle asynchronous image downloading for a more smoother experience. Alternatively, In the [APIExampleTMDB](https://github.com/Tc2r/APIs/tree/master/APIExampleTMDB) I used Picasso to do the same.

For UI I wanted to go for a more [Materialistic Design](https://material.io/guidelines/), implementing my own custom action bar which also serves as the search box.
