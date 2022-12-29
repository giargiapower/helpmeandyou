import 'dart:html';

import 'package:google_maps/google_maps.dart';

late DirectionsRenderer directionsDisplay;
final directionsService = DirectionsService();
late GMap map;

void main() {
  directionsDisplay = DirectionsRenderer();
  final chicago = LatLng(41.850033, -87.6500523);
  final mapOptions = MapOptions()
    ..zoom = 6
    ..center = chicago;
  map = GMap(document.getElementById('map-canvas') as HtmlElement, mapOptions);
  directionsDisplay.map = map;

  querySelector('#calcRoute')!.onClick.listen((e) => calcRoute());
}

void calcRoute() {
  final start = (document.getElementById('start') as SelectElement).value;
  final end = (document.getElementById('end') as SelectElement).value;
  final waypts = <DirectionsWaypoint>[];
  final checkboxArray = document.getElementById('waypoints') as SelectElement;
  for (final option in checkboxArray.options) {
    if (option.selected) {
      waypts.add(DirectionsWaypoint()
        ..location = option.value
        ..stopover = true);
    }
  }

  final request = DirectionsRequest()
    ..origin = start
    ..destination = end
    ..waypoints = waypts
    ..optimizeWaypoints = true
    ..travelMode = TravelMode.DRIVING;
  directionsService.route(request, (response, status) {
    if (status == DirectionsStatus.OK) {
      directionsDisplay.directions = response;
      final route = response!.routes![0];
      final summaryPanel = document.getElementById('directions_panel');
      final html = StringBuffer();
      // For each route, display summary information.
      for (var i = 0; i < route!.legs!.length; i++) {
        final leg = route.legs![i];
        final routeSegment = i + 1;
        html
          ..write('<b>Route Segment: $routeSegment</b><br>')
          ..write('${leg!.startAddress} to ')
          ..write('${leg.endAddress}<br>')
          ..write('${leg.distance!.text}<br><br>');
      }
      summaryPanel!.innerHtml = html.toString();
    }
  });
}
