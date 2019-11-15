package ca.ulaval.glo4002.booking.artists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.profits.Money;

public class ArtistService {

	private final ArtistRepository repository;
	private final ArtistConverter converter;

	@Inject
	public ArtistService(ArtistRepository repository) {
		this.repository = repository;
		this.converter = new ArtistConverter(repository);
		converter.convert();

	}

	public ArtistListDto getAllOrdered(String orderBy) {

		List<BookingArtist> bookingArtists = repository.findAll();
		List<String> artistNames = new ArrayList<>();

		if (orderBy.equalsIgnoreCase(ArtistOrderings.LOW_COSTS.toString())) {
			orderByLowCost(bookingArtists);
			artistNames.addAll(getArtistNames(bookingArtists));
		} else if (orderBy.equalsIgnoreCase(ArtistOrderings.MOST_POPULAR.toString())) {
			orderByMostPopular(bookingArtists);
			artistNames.addAll(getArtistNames(bookingArtists));
		} else {
			artistNames.addAll(getArtistNames(bookingArtists));
		}

		return new ArtistListDto(artistNames);
	}

	public ArtistListDto getAllUnordered() {

		List<BookingArtist> bookingArtists = repository.findAll();
		List<String> artistNames = getArtistNames(bookingArtists);
		return new ArtistListDto(artistNames);

	}

	private List<String> getArtistNames(List<BookingArtist> artists) {
		return artists.stream().map(BookingArtist::getName).collect(Collectors.toList());
	}

	private List<BookingArtist> orderByMostPopular(List<BookingArtist> artists) {
		Collections.sort(artists, (artist1, artist2) -> artist1.getPopularityRank() - artist2.getPopularityRank());
		return artists;
	}

	private List<BookingArtist> orderByLowCost(List<BookingArtist> artists) {
		artists.sort(
				Comparator.comparing(((Function<BookingArtist, Money>) BookingArtist::getCost).andThen(Money::getValue))
						.thenComparing(BookingArtist::getPopularityRank));
		return artists;
	}
}