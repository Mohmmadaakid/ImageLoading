
AssignmentImageLoading


This Android application efficiently loads and 
displays images in a scrollable grid using the Unsplash API. 
The project is implemented in Java following the MVVM architecture pattern.
 It leverages Glide for image loading and caching,
 and uses the com.jsibbold:zoomage library for zooming images to full-screen.


Features
Image Loading: Asynchronously loads images from the Unsplash API.
Scrollable Grid: Efficiently handles a large number of images,
 supporting up to 10,000 images in a scrollable grid.
Caching: Implements caching to store and retrieve images from memory and disk 
for improved performance.
Error Handling: Gracefully handles network errors and image loading failures,
 providing informative error
 messages or placeholders for failed image loads.
Zoom Functionality: Utilizes com.jsibbold:zoomage library to enable zooming 
capability on images.

dependencies

  com.github.bumptech.glide:glide
  com.squareup.retrofit2:retrofit
  com.squareup.retrofit2:converter-gson
  com.jsibbold:zoomage
