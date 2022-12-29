import 'dart:html';
import 'package:google_maps/google_maps.dart';

void main() {
  final map = GMap(
      document.getElementById('map-canvas') as HtmlElement,
      MapOptions()
        ..zoom = 4
        ..center = LatLng(-28, 137.883));

  map.data!
      .loadGeoJson('https://storage.googleapis.com/maps-devrel/google.json');
}
