import 'dart:html';
import 'package:google_maps/google_maps.dart';

void main() {
  final cairo = LatLng(30.064742, 31.249509);
  final map = GMap(
    document.getElementById('map') as HtmlElement,
    MapOptions()
      ..scaleControl = true
      ..center = cairo
      ..zoom = 10,
  );

  final infowindow = InfoWindow()..content = '<b>القاهرة</b>';
  final marker = Marker(MarkerOptions()
    ..map = map
    ..position = cairo);
  marker.onClick.listen((e) {
    infowindow.open(map, marker);
  });
}
