import 'dart:html';

import 'package:google_maps/google_maps.dart';
import 'package:google_maps/google_maps_places.dart';

late GMap map;
late InfoWindow infowindow;

void main() {
  AutocompleteService().getQueryPredictions(
      QueryAutocompletionRequest()..input = 'pizza near', callback);
}

void callback(List<QueryAutocompletePrediction?>? predictions,
    PlacesServiceStatus? status) {
  if (status != PlacesServiceStatus.OK) {
    window.alert('$status');
    return;
  }

  final results = document.getElementById('results') as UListElement;

  for (final prediction in predictions!) {
    results.innerHtml =
        '${results.innerHtml!}<li>${prediction!.description}</li>';
  }
}
