    package com.infinito.KYC.controller;
    
    import com.infinito.KYC.dto.LoginRequest;
    import com.infinito.KYC.dto.Response;
    import com.infinito.KYC.entity.User;
    import com.infinito.KYC.exception.OurException; // Import your custom exception
    import com.infinito.KYC.service.interfac.IUserService;
    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    
    @RestController
    @RequestMapping("/auth")
    public class AuthController {
    
        @Autowired
        private IUserService userService;
    
        @PostMapping(value = "/register", consumes = "application/json")
//      @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Response> register(@RequestBody User user) {
            Response response = userService.register(user);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    
        @PostMapping("/login")
        public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
            Response response = userService.login(loginRequest);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout(HttpServletRequest request) {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logged out successfully");
        }


        // Handle specific exceptions and return appropriate HTTP status codes
        @ExceptionHandler(OurException.class)
        public ResponseEntity<Response> handleOurException(OurException ex) {
            Response response = new Response();
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    
        // Optionally, handle generic exceptions
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Response> handleGenericException(Exception ex) {
            Response response = new Response();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An unexpected error occurred: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
