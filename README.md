# Mobile Assignment CS

## Approach :blush:
:star: I used MVVM with clean architecture because is easier to maintain, is easier to navigate in the project, the code are decoupled and is more readable for the team.  
:star: I changed the minimum api to 21 because the project asked sopport 5.0 +.  
:star: I used DI because promotes decoupling and testability.  
:star: I moved the api key and the base url in the build.gradle as it provides greater security and helps us to handle different flavors for example for debug and release.  
:star: I made use of Single activity architecture because is easier to share data  with safe args and has impeccable responsiveness also to see seamless transitions between the screens.  
:star: I created a global-dependencies file because allows you to manage the versions updates.


  	|──── app\ 
	 	|──── di\
	  		Contains all necessary class for dependencie injection		
	  	|──── network\
	  		Contains the retrofit builder
	  	|──── repository\
		  	This is the layer for access to the API 
		  	|──── models\
		  	       Has all the DTOs     
	  	|──── ui\
			UI layer this part has all the necessary elements for the control and design of the view
	  		|──── adapters
					Adapters for recyclerview views with their respective Holders. 
					This Part has the EndlessRecyclerViewScrollListener used for pagination
	  		|──── custom
					 Here is the rating view created in canvas
		|──── utils\ 
			Contains generic classes or functions for global use or that help throughout the project and avoid repetitions
## Some Used Libraries :books:
:blue_book:  [Retrofit]("https://github.com/square/retrofit"), [Gson](https://github.com/google/gson) -> For the network connection.  
:green_book: [Dagger](https://dagger.dev/) -> For the dependency injection.  
:orange_book: [Rxjava](https://github.com/ReactiveX/RxJava) -> Allows do the service call in another thread that the mainThread preventing ANR problems.  
:blue_book: [Glide](https://github.com/bumptech/glide) -> It provides loading and caching image besides facilitates transformations, adjust image size dynamically, get URL image , etc.  
:green_book: [Navigation and Safeargs](https://developer.android.com/guide/navigation) -> Single activity architecture.  
:orange_book: [Mockito](https://site.mockito.org/) -> For mock objects.  
## TODOs :pushpin:
:gear: More test even UI test with espresso.  
:gear: Use [Databinding](https://developer.android.com/topic/libraries/data-binding) for add logic to the view or [Viewbinding](https://developer.android.com/topic/libraries/view-binding) for replaces `findViewById` .  
:gear: Use [Groupie](https://github.com/lisawray/groupie) for do easier recyclerviews.  
:gear: Documentation.

## Demo
![](demo.gif)
