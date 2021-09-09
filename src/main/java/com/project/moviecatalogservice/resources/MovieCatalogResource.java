package com.project.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.moviecatalogservice.models.CatalogItem;
import com.project.moviecatalogservice.models.Movie;
import com.project.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	// Get all rated movie ID's
	// For each movie ID, call movie-info-service and get details
	// Put them all together
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable String userId) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		//We're saying get me the resource and unmarshal it into an object
		//One argument is an URL that needs to get
		//Second is the class that it needs to unmarshal to
		//It takes the payload and gonna return the movie object
		//Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);

		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 3));
		
		// Need to make call to API. Name and Desc should come from API.
		// Call for each rating of rated movie
		return ratings.stream().map(rating -> {
			//For every rated movie, I need to take that movie ID and 
			//I need to call the movie information API with that ID
			//For each iteration it's gonna make a separate call
			//And what it gets back here is that particular movie
			//So 1st time it's gonna make a call to movie ID "1234"
			//2nd - to '5678"
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies" + rating.getMovieId(), Movie.class);
			return new CatalogItem(movie.getName(), "Test", rating.getRating());
		})	
				.collect(Collectors.toList());
		
//		return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));
	
	}

}
