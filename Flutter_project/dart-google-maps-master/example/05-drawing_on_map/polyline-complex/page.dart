import 'dart:html' hide MouseEvent;
import 'package:google_maps/google_maps.dart';

late Polyline poly;
late GMap map;

void main() {
  final mapOptions = MapOptions()
    ..zoom = 7
    // Center the map on Chicago, USA.
    ..center = LatLng(41.879535, -87.624333);
  map = GMap(document.getElementById('map-canvas') as HtmlElement, mapOptions);

  final polyOptions = PolylineOptions()
    ..strokeColor = '#000000'
    ..strokeOpacity = 1.0
    ..strokeWeight = 3;
  poly = Polyline(polyOptions)..map = map;

  // Add a listener for the click event
  map.onClick.listen(addLatLng);
}

/// Handles click events on a map, and adds a new point to the Polyline.
/// @param {MouseEvent} mouseEvent
void addLatLng(MapMouseEvent e) {
  final path = poly.path!
    // Because path is an MVCArray, we can simply append a new coordinate
    // and it will automatically appear
    ..push(e.latLng);

  // Add a new marker at the new plotted point on the polyline.
  Marker(MarkerOptions()
    ..position = e.latLng
    ..title = '#${path.length}'
    ..map = map);
}
