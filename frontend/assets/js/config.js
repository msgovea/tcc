/* ============================================================
 * File: config.js
 * Configure routing
 * ============================================================ */

var translationsEN = {
    LOGIN:{
        HEADLINE: "Sign in to your urMusic account.",
        FORM:{
            USERNAME:       "E-mail",
            ERROR1:         "This field is required.",
            PASSWORD:       "Password",
            ERROR2_TITLE:   "Incorrect e-mail",
            ERROR2:         "E-mail or password is invalid.",
            ERROR3_TITLE:   "Confirm your account",
            ERROR3:         "To access your account you need confirm your account in your email.",
            ERROR4_TITLE:   "Your account is banned",
            ERROR4:         "Your account is banned.Contact us for clarification"
        },
        RECOVER:    "Password recovery",
        LOG_IN:     "Log in",
        FOOTER1:    "Create account",
        SIGN_UP:    "Sign up",  
        REGISTER:   "New to urMusic ?"
    },
    RECOVER:{
        HEADLINE: "Password recovery",
        SUBTITLE: "Fill in your email below to receive the password recovery link.",
        FORM:{
            EMAIL:          "Email",
            ERROR1:         "Enter a valid email.",
            SUCCESS1_TITLE: "Right email",
            SUCCESS1:       "Password recovery sent to the informed email.",
            ERROR2_TITLE:   "Wrong email",
            ERROR2:         "Email not registered."  
        },
        HELP: "Help? Contact Support",
        SEND: "Send"
    },
    RESET:{
        HEADLINE: "Set your new password",
        FORM:{
            PASSWORD: "New password",
            PH_PASSWORD: "Minimum of 6 Characters",
            ERROR1: "This field is required.",
            ERROR2: "Password should have at minimum 6 characters",
            CONF_PASSWORD: "Confirm new password",
            ERROR3: "Passwords must be the same",
            SUCCESS1_TITLE: "Password registered",
            SUCCESS1:       "New password registered.",   
            ERROR4:         "Password should have at maximum 12 characters"         
        },
        HELP: "Help? Contact Support",
        SEND: "Send",
        CANCEL: "Cancel"
    },
    REGISTER:{
        HEADLINE:   "urMusic makes it easy to enjoy what matters the most in your life!",
        SUBTITLE:   "Create your urMusic account. It's easy and fast!",
        FORM:{
            NAME:           "Name",
            ERROR1:         "This field is required.",
            USERNAME:       "Username",
            BIRTHDAY:       "Birthday",
            CITY:           "City",
            ERROR2:         "Enter a valid city.",
            STATE:          "State",
            ERROR3:         "Enter a valid state.",
            COUNTRY:        "Country",  
            ERROR4:         "Enter a valid country.",
            EMAIL:          "Email",
            ERROR5:         "Enter a valid email.",
            PASSWORD:       "Password",
            PH_PASSWORD:    "Minimum of 6 characters",
            ERROR6:         "Password should have at minimum 6 characters",
            CONF_PASSWORD:  "Confirm Password" ,
            ERROR7:         "Passwords must be the same",
            ERROR8:         "Password should have at maximum 12 characters",
            ERROR9:         "Characters not allowed.",
            NOTIF1_TITLE:   "Success",
            NOTIF1:         "Registration successfully completed! An account confirmation will be sent to the informed email.",
            ERROR10_TITLE:  "Incorrect registration",
            ERROR10:        "Already exists register for the informed email.",
            ERROR11:        "The date of birth should not be greater than today's date."
        },
        HELP: "Help? Contact Support",
        SEND: "Register"
    },
    HEADER:{
        NOTIFICATIONS1: "Read all notifications",
        SEARCH1: "Type anywhere to search",
        USER_INFO: {
            SETTINGS:   "Settings",
            FEEDBACK:   "Feedback",
            HELP:       "Help",
            LOGOUT:     "Logout"
        }
    },
    FOOTER:{
        TEXT1:  "All rights reserved.",
        TERMS:  "Terms of use",
        POLICY: "Privacy Policy",
        CRAFTED:"Hand-crafted",
        MADE:   "&amp; Made with Love ®"
    },
    CONFIRM_REG:{
        SUCCESS1_TITLE: "Success",
        SUCCESS1:       "Registration confirmed successfully!",
        HEADLINE1:      "Confirm your registration",
        HEADLINE2:      "Confirm your registration here",
        CONFIRM:        "Confirm",
        ERROR1_TITLE:   "",
        ERROR1:   ""
    },
    FEED:{
        POST:"Post"
    },

    GOSTO_MUSICAL:{
        CAD_GOSTOS:{
            HEADLINE: "Register your liking musical",
            SUBTITLE: "From your liking musical, you can find events and friends that enjoy the same as you!",
            SEND:     "Register"
        },
        CAD_FAVORITO:{
            HEADLINE: "Now, sign up for your favorite liking musical!",
            SEND:     "Register"
        },
        SUCCESS_TITLE:  "Success",
        SUCCESS:        "Music liking successfully registered.",
        ERROR1_TITLE:   "",
        ERROR1:         "Could not register your liking musical.",
        ERROR2_TITLE:   "",
        ERROR2:         "No liking musical selected."
    }
};

var translationsBR = {
    LOGIN:{
        HEADLINE: "Inicie a sessão na sua conta urMusic.",
        FORM:{
            USERNAME:       "E-mail",
            ERROR1:         "Este campo é obrigatório.",
            PASSWORD:       "Senha",
            ERROR2_TITLE:   "E-mail incorreto",
            ERROR2:         "E-mail ou senha inválido.",
            ERROR3_TITLE:   "Confirme sua conta",
            ERROR3:         "Para acessar sua conta, você precisa confirmar sua conta em seu e-mail.",
            ERROR4_TITLE:   "Sua conta está banida",
            ERROR4:         "Sua conta está banida. Contate-nos para esclarecimentos."
        },
        RECOVER:    "Recuperação de senha",
        LOG_IN:     "Entrar",
        FOOTER1:    "Criar conta",
        SIGN_UP:    "Criar conta",  
        REGISTER:   "Novo(a) em urMusic?"
    },
    RECOVER:{
        HEADLINE: "Recuperação de senha",
        SUBTITLE: "Preencha seu e-mail abaixo para receber o link de recuperação de senha.",
        FORM:{
            EMAIL:          "E-mail",
            ERROR1:         "Entre com um e-mail válido.",
            SUCCESS1_TITLE: "E-mail correto",
            SUCCESS1:       "Recuperação de senha enviada para o e-mail informado.",
            ERROR2_TITLE:   "E-mail errado",
            ERROR2:         "E-mail não cadastrado."  
        },
        HELP: "Help? Contact Support",
        SEND: "Enviar"
    },
    RESET:{
        HEADLINE: "Defina sua nova senha",
        FORM:{
            PASSWORD: "Nova senha",
            PH_PASSWORD: "Mínimo de 6 caracteres.",
            ERROR1: "Este campo é obrigatório.",
            ERROR2: "A senha deve ter no mínimo 6 caracteres.",
            CONF_PASSWORD: "Confirmar nova senha",
            ERROR3: "As senhas devem ser iguais.",
            SUCCESS1_TITLE: "Senha registrada",
            SUCCESS1:       "Nova senha registrada.",   
            ERROR4:         "A senha deve ter no máximo 12 caracteres."         
        },
        HELP: "Help? Contact Support",
        SEND: "Enviar",
        CANCEL: "Cancelar"
    },
     REGISTER:{
        HEADLINE:   "O urMusic torna fácil desfrutar o que mais importa na sua vida!",
        SUBTITLE:   "Crie sua conta urMusic. É fácil e rápido!",
        FORM:{
            NAME:           "Nome",
            ERROR1:         "Este campo é obrigatório.",
            USERNAME:       "Usuário",
            BIRTHDAY:       "Data de nascimento",
            CITY:           "Cidade",
            ERROR2:         "Insira uma cidade válida.",
            STATE:          "Estado",
            ERROR3:         "Insira um estado válido.",
            COUNTRY:        "País",  
            ERROR4:         "Insira um país válido.",
            EMAIL:          "E-mail",
            ERROR5:         "Insira um e-mail válido.",
            PASSWORD:       "Senha",
            PH_PASSWORD:    "Mínimo de 6 caracteres",
            ERROR6:         "A senha deve ter no mínimo 6 caracteres.",
            CONF_PASSWORD:  "Confirmar senha" ,
            ERROR7:         "As senhas devem ser iguais.",
            ERROR8:         "A senha deve ter no máximo 12 caracteres.",
            ERROR9:         "Caracteres não permitidos.",
            NOTIF1_TITLE:   "Sucesso",
            NOTIF1:         "Cadastro realizado com sucesso! Uma confirmação da conta será enviada para o e-mail informado.",
            ERROR10_TITLE:  "Cadastro incorreto",
            ERROR10:        "Já existe cadastro para o e-mail informado.",
            ERROR11:        "A data de nascimento não deve ser superior a data de hoje."
        },
        HELP: "Help? Contact Support",
        SEND: "Cadastrar"
     },

     HEADER:{
        NOTIFICATIONS1: "Ler todas as notificações",
        SEARCH1: "Digite em qualquer lugar para pesquisar",
        USER_INFO: {
            SETTINGS:   "Configurações",
            FEEDBACK:   "Comentários",
            HELP:       "Ajuda",
            LOGOUT:     "Sair"
        }
    },
    FOOTER:{
        TEXT1:  "Todos os direitos reservados.",
        TERMS:  "Termos de uso",
        POLICY: "Política de privacidade",
        CRAFTED:"Feito à mão",
        MADE:   "&amp; Feito com amor ®"
    },

     CONFIRM_REG:{
        SUCCESS1_TITLE: "Sucesso",
        SUCCESS1:       "Cadastro confirmado com sucesso!",
        HEADLINE1:      "Confirme o seu cadastro",
        HEADLINE2:      "Confirme o seu cadastro aqui",
        CONFIRM:        "Confirmar",
        ERROR1_TITLE:   "",
        ERROR1:   ""
    },

     GOSTO_MUSICAL:{
        CAD_GOSTOS:{
            HEADLINE: "Cadastre seu gosto musical",
            SUBTITLE: "A partir do seu gosto musical, você poderá encontrar eventos e amigos que gostam do mesmo que você!",
            SEND:     "Cadastrar"
        },
        CAD_FAVORITO:{
            HEADLINE: "Agora, cadastre seu gosto musical favorito!",
            SEND:     "Cadastrar"
        },
        SUCCESS_TITLE:  "Sucesso",
        SUCCESS:        "Gosto musical cadastrado com sucesso!",
        ERROR1_TITLE:   "",
        ERROR1:         "Não foi possível cadastrar o seu gosto musical.",
        ERROR2_TITLE:   "",
        ERROR2:         "Nenhum gosto musical selecionado."
    }
};

angular.module('app')
    .config(['$translateProvider', function ($translateProvider) {
        //utilizado para garantir a segurança
        $translateProvider.useSanitizeValueStrategy('escaped');
        $translateProvider
        .translations('en', translationsEN)
        .translations('br', translationsBR)
        .preferredLanguage('br');
    }])
    .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider',

        function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {
            $urlRouterProvider
                .otherwise('/app/feed');

            $stateProvider

                .state('app', {
                    abstract: true,
                    url: "/app",
                    controller: 'HeaderCtrl',
                    data: {
                        requireLogin: true,
                        requireAdmin: false
                    },
                    templateUrl: "tpl/app.html"
                })
                
                .state('app.dashboard', {
                    url: "/dashboard",
                    templateUrl: "tpl/dashboard.html",
                    controller: 'DashboardCtrl',
                    
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'nvd3',
                                    'mapplic',
                                    'rickshaw',
                                    'metrojs',
                                    'sparkline',
                                    'skycons',
                                    'switchery'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load([
                                        'assets/js/controllers/dashboard.js'
                                    ]);
                                });
                        }]
                    }
                })

            // Email app 
            .state('app.email', {
                    abstract: true,
                    url: '/email',
                    templateUrl: 'tpl/apps/email/email.html',
                    data: {
                        requireLogin: false,
                        requireAdmin: false
                    },
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'menuclipper',
                                    'wysihtml5'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load([
                                        'assets/js/apps/email/service.js',
                                        'assets/js/apps/email/email.js'
                                    ])
                                });
                        }]
                    }
                })
            .state('app.email.inbox', {
                url: '/inbox/:emailId',
                templateUrl: 'tpl/apps/email/email_inbox.html'
            })
            .state('app.email.compose', {
                url: '/compose',
                templateUrl: 'tpl/apps/email/email_compose.html'
            })
            // Social app
            .state('app.social', {
                url: '/social',
                data: {
                        requireLogin: false
                },
                templateUrl: 'tpl/apps/social/social.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'isotope',
                                'stepsForm'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load([
                                    'pages/js/pages.social.min.js',
                                    'assets/js/apps/social/social.js'
                                ])
                            });
                    }]
                }
            })
            .state('app.feed', {
                url: '/feed',
                data: {
                        requireLogin: false
                },
                templateUrl: 'tpl/apps/social/feed.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'isotope',
                                'stepsForm'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load([
                                    'pages/js/pages.social.min.js',
                                    'assets/js/apps/social/feed.js'
                                ])
                            });
                    }]
                }
            })
            
            //Calendar app
            .state('app.calendar', {
                url: '/calendar',
                templateUrl: 'tpl/apps/calendar/calendar.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'switchery',
                                'moment-locales',
                                'interact'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load([
                                    'pages/js/pages.calendar.min.js',
                                    'assets/js/apps/calendar/calendar.js'
                                ])
                            });
                    }]
                }
            })
            .state('app.builder', {
                url: '/builder',
                template: '<div></div>',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            'assets/js/controllers/builder.js',
                        ]);
                    }]
                }
            })

            .state('app.layouts', {
                url: '/layouts',
                template: '<div ui-view></div>'
            })
            .state('app.layouts.default', {
                url: '/default',
                templateUrl: 'tpl/layouts_default.html'
            })
            .state('app.layouts.secondary', {
                url: '/secondary',
                templateUrl: 'tpl/layouts_secondary.html'
            })
            .state('app.layouts.horizontal', {
                url: '/horizontal',
                templateUrl: 'tpl/layouts_horizontal.html'
            })
            .state('app.layouts.rtl', {
                url: '/rtl',
                controller: 'RTLCtrl',
                templateUrl: 'tpl/layouts_default.html',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            'assets/js/controllers/rtl.js',
                        ]);
                    }]
                }
            })
            .state('app.layouts.columns', {
                url: '/columns',
                templateUrl: 'tpl/layouts_columns.html'
            })

            // Boxed app
            .state('boxed', {
                url: "/boxed",
                templateUrl: "tpl/app.boxed.html"
            })

            // UI Elements 
            .state('app.ui', {
                    url: '/ui',
                    template: '<div ui-view></div>'
                })
                .state('app.ui.color', {
                    url: '/color',
                    templateUrl: 'tpl/ui_color.html'
                })
                .state('app.ui.typo', {
                    url: '/typo',
                    templateUrl: 'tpl/ui_typo.html'
                })
                .state('app.ui.icons', {
                    url: '/icons',
                    templateUrl: 'tpl/ui_icons.html',
                    controller: 'IconsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'sieve',
                                    'line-icons'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load([
                                        'assets/js/controllers/icons.js'
                                    ])
                                });
                        }]
                    }
                })
                .state('app.ui.buttons', {
                    url: '/buttons',
                    templateUrl: 'tpl/ui_buttons.html'
                })
                .state('app.ui.notifications', {
                    url: '/notifications',
                    templateUrl: 'tpl/ui_notifications.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'assets/js/controllers/notifications.js'
                            ]);
                        }]
                    }
                })
                .state('app.ui.modals', {
                    url: '/modals',
                    templateUrl: 'tpl/ui_modals.html',
                    controller: 'ModalsCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'assets/js/controllers/modals.js'
                            ]);
                        }]
                    }
                })
                .state('app.ui.progress', {
                    url: '/progress',
                    templateUrl: 'tpl/ui_progress.html'
                })
                .state('app.ui.tabs', {
                    url: '/tabs',
                    templateUrl: 'tpl/ui_tabs.html',
                    resolve: { 
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'tabcollapse'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            });
                        }]
                    }
                })
                .state('app.ui.sliders', {
                    url: '/sliders',
                    templateUrl: 'tpl/ui_sliders.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'noUiSlider',
                                'ionRangeSlider'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            });
                        }]
                    }
                })
                .state('app.ui.treeview', {
                    url: '/treeview',
                    templateUrl: 'tpl/ui_treeview.html',
                    controller: 'TreeCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'navTree'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/treeview.js');
                                });
                        }]
                    }
                })
                .state('app.ui.nestables', {
                    url: '/nestables',
                    templateUrl: 'tpl/ui_nestable.html',
                    controller: 'NestableCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'nestable'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/nestable.js');
                                });
                        }]
                    }
                })

            // Form elements
            .state('app.forms', {
                    url: '/forms',
                    template: '<div ui-view></div>'
                })
                .state('app.forms.elements', {
                    url: '/elements',
                    templateUrl: 'tpl/forms_elements.html',
                    controller: 'FormElemCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'switchery',
                                    'select',
                                    'moment',
                                    'datepicker',
                                    'daterangepicker',
                                    'timepicker',
                                    'inputMask',
                                    'autonumeric',
                                    'wysihtml5',
                                    'summernote',
                                    'tagsInput',
                                    'dropzone',
                                    'typehead'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/forms_elements.js');
                                });
                        }]
                    }
                })
                .state('app.forms.layouts', {
                    url: '/layouts',
                    templateUrl: 'tpl/forms_layouts.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'datepicker',
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/forms_layouts.js');
                                });
                        }]
                    }
                })
                .state('app.forms.wizard', {
                    url: '/wizard',
                    templateUrl: 'tpl/forms_wizard.html',
                    controller: 'FormWizardCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'wizard'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/forms_wizard.js');
                                });
                        }]
                    }
                })

            // Portlets
            .state('app.portlets', {
                url: '/portlets',
                templateUrl: 'tpl/portlets.html',
                controller: 'PortletCtrl',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            'assets/js/controllers/portlets.js'
                        ]);
                    }]
                }
            })

            // Views
            .state('app.views', {
                url: '/views',
                templateUrl: 'tpl/views.html'
            })

            // Tables
            .state('app.tables', {
                    url: '/tables',
                    template: '<div ui-view></div>'
                })
                .state('app.tables.basic', {
                    url: '/basic',
                    templateUrl: 'tpl/tables_basic.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'dataTables'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/tables.js');
                                });
                        }]
                    }
                })
                .state('app.tables.dataTables', {
                    url: '/dataTables',
                    templateUrl: 'tpl/tables_dataTables.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'dataTables',
                                    'ui-grid' 
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/dataTables.js');
                                });
                        }]
                    }
                })

            // Maps
            .state('app.maps', {
                    url: '/maps',
                    template: '<div class="full-height full-width" ui-view></div>'
                })
                .state('app.maps.google', {
                    url: '/google',
                    templateUrl: 'tpl/maps_google_map.html',
                    controller: 'GoogleMapCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'google-map'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/google_map.js')
                                        .then(function() {
                                            return loadGoogleMaps();
                                        });
                                });
                        }]
                    }
                })
                .state('app.maps.vector', {
                    url: '/vector',
                    templateUrl: 'tpl/maps_vector_map.html',
                    controller: 'VectorMapCtrl',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'mapplic',
                                    'select'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/vector_map.js');
                                });
                        }]
                    }
                })

            // Charts
            .state('app.charts', {
                url: '/charts',
                templateUrl: 'tpl/charts.html',
                controller: 'ChartsCtrl',
                resolve: {
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                        return $ocLazyLoad.load([
                                'nvd3',
                                'rickshaw',
                                'sparkline'
                            ], {
                                insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load('assets/js/controllers/charts.js');
                            });
                    }]
                }
            })

            // Extras
            .state('app.extra', {
                    url: '/extra',
                    template: '<div ui-view></div>'
                })
                .state('app.extra.invoice', {
                    url: '/invoice',
                    templateUrl: 'tpl/extra_invoice.html'
                })
                .state('app.extra.blank', {
                    url: '/blank',
                    templateUrl: 'tpl/extra_blank.html'
                })
                .state('app.extra.gallery', {
                    url: '/gallery',
                    templateUrl: 'tpl/extra_gallery.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                    'isotope',
                                    'codropsDialogFx',
                                    'metrojs',
                                    'owlCarousel',
                                    'noUiSlider'
                                ], {
                                    insertBefore: '#lazyload_placeholder'
                                })
                                .then(function() {
                                    return $ocLazyLoad.load('assets/js/controllers/gallery.js');
                                });
                        }]
                    }
                })
                .state('app.extra.timeline', {
                    url: '/timeline',
                    templateUrl: 'tpl/extra_timeline.html'
                })

            // Extra - Others
            .state('access', {
                    url: '/access',
                    data: {
                        requireLogin: false
                    },
                    template: '<div class="full-height" ui-view></div>'
                })
                .state('access.confirmarCadastro',{
                    url: "/confirmarCadastro/:idUsuario/:emailHash",
                    templateUrl: "tpl/confirmar_cadastro.html",
                    data: {
                        requireLogin: false
                    },
                })
                .state('access.404', {
                    url: '/404',
                    templateUrl: 'tpl/404.html'
                })
                .state('access.500', {
                    url: '/500',
                    templateUrl: 'tpl/500.html'
                })
                .state('access.login', {
                    url: '/login',
                    data: {
                        requireLogin: false
                    },
                    controller: 'LoginCtrl',
                    templateUrl: 'tpl/login.html'
                })
                .state('access.register', {
                    url: '/register',
                    controller: 'RegisterCtrl',
                    templateUrl: 'tpl/register.html',
                    resolve: {
                        deps: ['$ocLazyLoad', function($ocLazyLoad) {
                            return $ocLazyLoad.load([
                                'datepicker'
                            ], {
                                 insertBefore: '#lazyload_placeholder'
                            })
                            .then(function() {
                                return $ocLazyLoad.load('assets/js/controllers/register.js');
                            });
                        }]
                    }
                })
                .state('access.lock_screen', {
                    url: '/lock_screen',
                    templateUrl: 'tpl/extra_lock_screen.html'
                })
                .state('access.recuperarSenha', {
                    url: '/recuperarSenha',
                    controller: 'RecuperarSenhaCtrl',
                    templateUrl: 'tpl/recuperar_senha.html'
                })
                .state('access.redefinirSenha', {
                    url: '/redefinirSenha/:id/:codigo',
                    controller: 'RedefinirSenhaCtrl',
                    templateUrl: 'tpl/redefinir_senha.html'
                })

        }
    ]);