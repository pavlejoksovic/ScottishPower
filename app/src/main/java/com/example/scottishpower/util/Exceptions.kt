package com.example.scottishpower.util

class PlaceholderNetworkingException(httpStatus: String): Exception("Placeholder API failed with HTTP status: $httpStatus")

class PlaceholderMissingDataException: Exception("Placeholder API responded missing or incomplete data")