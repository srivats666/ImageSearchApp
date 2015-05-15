# ImageSearchApp
This is an Android Image search app.

Time spent: 16 hours spent in total

Completed user stories:

User can enter a search query that will display a grid of image results from the Google Image API.

User can click on "settings" which allows selection of advanced search options to filter results.

User can configure advanced search filters such as:
Size (small, medium, large, extra-large)
Color filter (black, blue, brown, gray, green, etc...)
Type (faces, photo, clip art, line art)
Site (espn.com)

Subsequent searches will have any filters applied to the search results.

User can tap on any image in results to see the image full-screen.

User can scroll down “infinitely” to continue loading more image results (up to 8 pages).

Overriding onFailure method in JsonHttpResponseHandler to handle error messages and notify user appropriately.

Externalizing error messages, spinner and labels to strings.xml.

Advanced: Robust error handling, check if internet is available, handle error cases, network failures.

Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText.

Advanced: User can share an image to their friends or email it to themselves.

Advanced: Replace Filter Settings Activity with a lightweight modal overlay.

Advanced: Improve the user interface and experiment with image assets and/or styling and coloring.

Advanced: Improving Performance with the ViewHolder Pattern.

Bonus: Use the StaggeredGridView to display improve the grid of image results.

Bonus: User can zoom or pan images displayed in full-screen detail view

