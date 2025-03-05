<?php

header("Content-Type: application/json");

// Obtener el método HTTP de la solicitud
$method = $_SERVER['REQUEST_METHOD'];

switch ($method) {
    case 'GET':
        echo json_encode(["message" => "Solicitud GET recibida"]);
        break;
    
    case 'POST':
        $data = json_decode(file_get_contents("php://input"), true);
        echo json_encode(["message" => "Solicitud POST recibida", "data" => $data]);
        break;

    case 'PUT':
        $data = json_decode(file_get_contents("php://input"), true);
        echo json_encode(["message" => "Solicitud PUT recibida", "data" => $data]);
        break;

    case 'DELETE':
        echo json_encode(["message" => "Solicitud DELETE recibida"]);
        break;

    default:
        echo json_encode(["error" => "Método no permitido"]);
        http_response_code(405); // Método no permitido
        break;
}
