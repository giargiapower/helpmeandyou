import 'dart:convert';
import 'dart:html';
import 'dart:html' as html show MouseEvent;

import 'package:google_maps/google_maps.dart';
import 'package:js/js_util.dart';
import 'package:js_wrapping/js_wrapping.dart';

late GMap map;

void main() {
  initMap();
  initEvents();
}

void initMap() {
  // set up the map
  map = GMap(
      document.getElementById('map-canvas') as HtmlElement,
      MapOptions()
        ..zoom = 2
        ..center = LatLng(0, 0));
}

void loadGeoJsonString(String geoString) {
  final geojson = json.decode(geoString);
  map.data!.addGeoJson(jsify(geojson));
  zoom(map);
}

/// Update a map's viewport to fit each geometry in a dataset
/// @param {google.maps.Map} map The map to adjust
void zoom(GMap map) {
  final bounds = LatLngBounds();
  map.data!.forEach((feature) {
    // TODO: replace with tearoff once issue is fixed
    // ignore: unnecessary_lambdas, https://github.com/dart-lang/sdk/issues/32370
    processPoints(feature!.geometry!, (e) => bounds.extend(e));
  });
  map.fitBounds(bounds);
}

/// Process each point in a Geometry, regardless of how deep the points may lie.
/// @param {google.maps.Data.Geometry} geometry The structure to process
/// @param {function(google.maps.LatLng)} callback A function to call on each
/// LatLng point encountered (e.g. Array.push)
void processPoints(
    DataGeometry geometry, LatLngBounds? callback(LatLng? point)) {
  if (geometry is DataPoint) {
    callback(geometry.get());
  } else if (geometry is DataGeometryCollection) {
    for (final g in geometry.array!) {
      processPoints(g!, callback);
    }
  }
}

/* DOM (drag/drop) functions */

void initEvents() {
  // set up the drag & drop events
  final mapContainer = document.getElementById('map-canvas')!;
  final dropContainer = document.getElementById('drop-container')!;

  // map-specific events
  mapContainer.onDragEnter.listen(showPanel);

  // overlay specific events (since it only appears once drag starts)
  dropContainer.onDragOver.listen(showPanel);
  dropContainer.onDrop.listen(handleDrop);
  dropContainer.onDragLeave.listen(hidePanel);
}

void showPanel(html.MouseEvent e) {
  e
    ..stopPropagation()
    ..preventDefault();
  document.getElementById('drop-container')!.style.display = 'block';
}

void hidePanel(html.MouseEvent e) {
  document.getElementById('drop-container')!.style.display = 'none';
}

void handleDrop(html.MouseEvent e) {
  e
    ..stopPropagation()
    ..preventDefault();
  hidePanel(e);

  final files = e.dataTransfer.files!;
  if (files.isNotEmpty) {
    // process file(s) being dropped
    // grab the file data from each file
    for (final file in files) {
      final reader = FileReader();
      reader.onLoad.listen((e) {
        loadGeoJsonString((e.target as FileReader).result as String);
      });
      reader.onError.listen((e) {
        window.console.error('reading failed');
      });
      reader.readAsText(file);
    }
  } else {
    // process non-file (e.g. text or html) content being dropped
    // grab the plain text version of the data
    final plainText = e.dataTransfer.getData('text/plain');
    // ignore: unnecessary_null_comparison
    if (plainText != null) {
      loadGeoJsonString(plainText);
    }
  }
}
